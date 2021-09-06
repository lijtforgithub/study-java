### NIO
- FileChannel 是不支持非阻塞的。
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
### 同步异步/阻塞非阻塞
> 同步异步，阻塞非阻塞的不同在于他们对信息的关注点不同: 同步异步关注的是消息通信机制 阻塞非阻塞关注的是等待消息时的状态

一个人要烧开一壶水，有很多种方式。  
这个人开火烧水，还得这个人本人监督着直到水烧开，这是同步。 这个人把水放在水上烧，水烧开后不一定由这个人处理，这叫异步。
这个人在等水烧开的过程中什么事情都不干，就等着水烧开，这叫阻塞。如果这个人还去做其他的事情，这叫做非阻塞。

- 同步阻塞  
一个人烧水，在水烧开之前，他就一直在旁边等着，什么时候水烧开了他才进行下一步的操作，这就是同步阻塞。
- 同步非阻塞  
一个人烧水，在水放到火上后，他就去干别的事情了，但是水开之后还是由这个人处理，这就是同步非阻塞。
- 异步阻塞  
一个人烧水，给烧水的壶设置了一个铃，水开铃就会响，然后就去做别的事情了。水开之后的事情不一定是他处理了。他也可以在旁边等着水烧开。这种情况非常少见。
- 异步非阻塞  
一个人烧水，开火之后设置好水开的处理程序，不由他自己处理。异步非阻塞是经常用到的。
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
### Linux文件描述符默认1024
如果是root账户 则没限制 ulimit -a