### NIO
- 网络IO流InputStream.available()不能用。
- FileChannel 是不支持非阻塞的。
- [IO + Redis](https://www.cnblogs.com/myseries/p/11733861.html)
   1. 不用重复传递。我们调用epoll_wait时就相当于以往调用select/poll，但是这时却不用传递socket句柄给内核，因为内核已经在epoll_ctl中拿到了要监控的句柄列表。
   2. 在内核里，一切皆文件。epoll向内核注册了一个文件系统，用于存储上述的被监控socket。当你调用epoll_create时，就会在这个虚拟的epoll文件系统里创建一个file结点。这个file不是普通文件，它只服务于epoll。
   epoll在被内核初始化时（操作系统启动），同时会开辟出epoll自己的内核高速cache区，用于安置每一个我们想监控的socket，这些socket会以红黑树的形式保存在内核cache里，以支持快速的查找、插入、删除。
   这个内核高速cache区，就是建立连续的物理内存页，然后在之上建立slab层，简单的说，就是物理上分配好你想要的size的内存对象，每次使用时都是使用空闲的已分配好的对象。
   3. 极其高效的原因：我们在调用epoll_create时，内核除了帮我们在epoll文件系统里建了个file结点，在内核cache里建了个红黑树用于存储以后epoll_ctl传来的socket外，还会再建立一个list链表，用于存储准备就绪的事件，
   当epoll_wait调用时，仅仅观察这个list链表里有没有数据即可。有数据就返回，没有数据就sleep，等到timeout时间到后即使链表没数据也返回。

   这个准备就绪list链表是怎么维护的呢？当我们执行epoll_ctl时，除了把socket放到epoll文件系统里file对象对应的红黑树上之外，还会给内核中断处理程序注册一个回调函数，告诉内核，如果这个句柄的中断到了，
   就把它放到准备就绪list链表里。所以，当一个socket上有数据到了，内核在把网卡上的数据copy到内核中后就来把socket插入到准备就绪链表里了。epoll的基础就是回调呀！
   一颗红黑树，一张准备就绪句柄链表，少量的内核cache，就帮我们解决了大并发下的socket处理问题。执行epoll_create时，创建了红黑树和就绪链表，执行epoll_ctl时，如果增加socket句柄，则检查在红黑树中是否存在，存在立即返回，不存在则添加到树干上，然后向内核注册回调函数，用于当中断事件来临时向准备就绪链表中插入数据。执行epoll_wait时立刻返回准备就绪链表里的数据即可。

   epoll读有的两种模式LT和ET。无论是LT和ET模式，都适用于以上所说的流程。区别是，LT模式下，只要一个句柄上的事件一次没有处理完，会在以后调用epoll_wait时次次返回这个句柄，而ET模式仅在第一次返回。
   LT和ET都是电子里面的术语，ET是边缘触发，LT是水平触发，一个表示只有在变化的边际触发，一个表示在某个阶段都会触发。
   LT, ET这件事怎么做到的呢？当一个socket句柄上有事件时，内核会把该句柄插入上面所说的准备就绪list链表，这时我们调用epoll_wait，会把准备就绪的socket拷贝到用户态内存，
   然后清空准备就绪list链表，最后，epoll_wait干了件事，就是检查这些socket，如果不是ET模式（就是LT模式的句柄了），并且这些socket上确实有未处理的事件时，又把该句柄放回到刚刚清空的准备就绪链表了。所以，非ET的句柄，只要它上面还有事件，epoll_wait
   每次都会返回这个句柄。
### 多路(IO)复用器
通过一个系统调用 获得其中的IO(注册的fd)的状态 然后由程序自己对有状态的IO 进行读写(select/poll/epoll)
- select: synchronous I/O multiplexing 受FD_SETSIZE限制 现在基本上已经不用了(POSIX 各种操作系统都有 比较容易实现)
- poll: 和select一样 只是没有FD_SETSIZE限制 select和poll 都是单系统调用
> 无论NIO和select/poll都要遍历所有的IO询问状态 只不过NIO遍历的过程成本在用户态内核态切换   
> select/poll遍历的过程触发了一次系统调用 用户态内核态的切换 过程中 把fds传递给内核 内核重新根据用户的这次调用传过来的fds遍历修改状态
###### select/poll的问题
1. 每次都要重新重复传递fds 每次内核被调了之后 
2. 针对这次调用 触发一个遍历fds全量的复杂度
#### epoll: (Unix:kqueue)  
网卡有数据到达会产生中断->回调callback(event事件 -> 回调处理事件)   
在epoll之前的回调 只是完成了将网卡发来的数据 走内核网络协议栈最终关联到fd的buffer 所以 你某一事件如果从APP询问内核某一个或这某些fd是有R/W 有状态返回  
在内核在回调处理中加入 把有状态的fd复制到链表中
###### 优点
在Linux2.6内核中 epoll没有fd限制 使用事件通知 通过epoll_ctl注册fd 一旦该fd就绪 内核就会采用callback机制来激活对应的fd
1. 没有fd限制 支持的fd上限是操作系统的最大文件句柄数 1G内存大约支持10W个句柄
2. 效率提高 使用回调通知而不是轮询的方式 不会随着fd的增加而效率下降
3. 通过callback机制通知 内核和用户空间mmap同一块内存实现
###### Linux 核心函数
1. epoll_create 在Linux内核里面申请一个文件系统红黑树 返回epoll对象 也是一个fd(ep_fd)
2. epoll_ctl 操作epoll对象 在这个对象里面修改添加删除的对应的链接fd 绑定一个callback函数
3. epoll_wait 判断并完成对应的IO操作(红黑树中有状态的fd复制到链表 直接得到一个有状态的fd集合 不用内核遍历一次了)
```azure
-Djava.nio.channels.spi.SelectorProvider=sun.nio.ch.EPollSelectorProvider
                                         sun.nio.ch.PollSelectorProvider
```
#### 写事件OP_WRITE
如果有channel在Selector上注册了SelectionKey.OP_WRITE，在调用selector.select();时，系统会检查内核写缓冲区是否可写：  
如果可写，selector.select();立即返回，进入key.isWritable()  
何时不可写？比如缓冲区已满，channel调用了shutdownOutPut等  
当然除了注册写事件，你也可以在channel直接调用write(…)，也可以将数据发出去，但这样不够灵活，而且可能浪费CPU。  
要触发写事件，需要先向 selector 注册该通道的写事件，跟注册读事件一样，当底层写缓冲区有空闲就会触发写事件了，而一般来说底层的写缓冲区大部分都是空闲的。所以一般只要注册了写事件，就会立马触发了，为了避免 cpu 空转，在写操作完成后需要把写事件取消掉，然后下次再有写操作时重新注册写事件。

#### 只关注IO 不关注从IO读写完之后的事情
- 同步：程序自己读写 内核只靠诉你数据到达了 还是要程序主动去调用内核读数据
- 异步：内核完成读写 程序看上去没有访问IO 设置一个回调的buf (目前只有windows的iocp支持)
- 阻塞：内核BLOCKING
- 非阻塞：内核NONBLOCKING
> Linux以及成熟的框架Netty
- 同步阻塞：程序自己读取 调用了方法一直等待有效返回结果
- 同步非阻塞：程序自己读取 调用方法一瞬间 给出是否读到(程序要解决下一次啥时候再去读：循环)
- 异步：尽量不要去讨论 只讨论IO模型下 Linux目前没有通用的内核异步处理方案。异步阻塞没有意义。异步非阻塞(Win AIO)
### ServerSocket
- ServerSocket()throws IOException
- ServerSocket(int port)throws IOException
- ServerSocket(int port, int backlog)throws IOException
- ServerSocket(int port, int backlog, InetAddress bindAddr)throws IOException
 1. port服务端要监听的端口；backlog客户端连接请求的队列长度；bindAddr服务端绑定IP。
 2. 如果端口被占用或者没有权限使用某些端口会抛出BindException错误。譬如1~1023的端口需要管理员才拥有权限绑定。
 3. 如果设置端口为0，则系统会自动为其分配一个端口。
 4. bindAddr用于绑定服务器IP，为什么会有这样的设置呢，譬如有些机器有多个网卡。
 5. ServerSocket一旦绑定了监听端口，就无法更改。ServerSocket()可以实现在绑定端口前设置其他的参数。
### 零拷贝
1. 传统 IO 执行的话需要 4 次上下文切换(用户态 -> 内核态 -> 用户态 -> 内核态 -> 用户态)和 4 次拷贝(磁盘文件 DMA 拷贝到内核缓冲区，内核缓冲区 CPU 拷贝到用户缓冲区，用户缓冲区 CPU 拷贝到 Socket 缓冲区，Socket 缓冲区 DMA 
   拷贝到协议引擎)
2. mmap 将磁盘文件映射到内存，支持读和写，对内存的操作会反映在磁盘文件上，适合小数据量读写，需要 4 次上下文切换(用户态 -> 内核态 -> 用户态 -> 内核态 -> 用户态)和3 次拷贝(磁盘文件DMA拷贝到内核缓冲区，内核缓冲区 CPU 拷贝到 Socket 
   缓冲区，Socket 缓冲区 DMA 拷贝到协议引擎)
 > RocketMQ 中就是使用的 mmap 来提升磁盘文件的读写性能。
3. sendfile 是将读到内核空间的数据，转到网络协议引擎，进行网络发送，适合大文件传输，只需要 2 次上下文切换(用户态 -> 内核态 -> 用户态)和 2 次拷贝(磁盘文件 DMA 拷贝到内核缓冲区，内核缓冲区 DMA 拷贝到协议引擎)。只需要从内核缓冲区拷贝一些 offset 和 
   length 到 Socket 缓冲区。
 > Kafka 和 Tomcat 内部使用就是 sendFile 这种零拷贝。
### 硬中断和软中断 打断CPU
1. 输入设备触发硬中断(时钟中断 晶振 时间片 进程切换 保护现场：寄存器的数据->内存)
2. 程序读硬盘或者网卡 发送系统调用 需要软中断 int0x80(sysenter原语 intel CPU)

```
;hello.asm
;write(int fd, const void *buffer, size_t nbytes)
;fd 文件描述符 file descriptor - linux下一切皆文件
​
section data
    msg db "Hello", 0xA
    len equ $ - msg
​
section .text
global _start
_start:
​
    mov edx, len
    mov ecx, msg
    mov ebx, 1 ;文件描述符1 std_out
    mov eax, 4 ;write函数系统调用号 4
    int 0x80
​
    mov ebx, 0
    mov eax, 1 ;exit函数系统调用号
    int 0x80
```
### TCP
1. 滑动窗口(解决网络拥塞)MTU(最大传输单元 网卡)/MSS(是TCP协议定义的一个选项，MSS选项用于在TCP连接建立时，收发双方协商通信时每一个报文段所能承载的最大数据长度)
2. 内核接收缓存满了 丢弃后面进来的 既要提高效率 也不能发爆了 客户端应该根据服务端ack的窗口大小 决定是不是要阻塞一会再发
3. 三次握手(网络信道不可靠 证明客户端和服务端的接收和发送IO都没问题)和四次挥手(可靠的连接 客户端和服务端都想断开 而且服务端要等把数据包发送完 然后提出分手 最后都释放资源)
4. 面向连接的(三次握手之后双方都开辟了资源 建立了Socket 四元组)可靠的(每次报文都ack)传输协议
5. TCP keepalive 会有心跳检测包 是TCP保鲜定时器
6. MSL是Maximum Segment Lifetime报文最大生存时间 TCP的TIME_WAIT状态也称为2MSL等待状态 最后一次ACK不确定有没有收到(没收到的话 继续FIN) 所以留了这个时间 然后才释放资源(连接服务器的名额)
### Linux文件描述符默认1024
如果是root账户 则没限制 ulimit -a