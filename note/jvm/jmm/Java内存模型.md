### 硬件层数据一致性
- 总线锁
- 一致性协议。  
*intel用[MESI](https://www.cnblogs.com/z00377750/p/9180644.html)（Modified Exclusive Shared Invalid）  
CPU中每个缓存行（cache line）使用4种状态进行标记（使用额外的两位(bit)表示）* **读取缓存以cache line为基本单位，目前64字节；使用缓存行的对齐能够提高效率。**
> 现代CPU的数据一致性实现 = 缓存锁(MESI ...) + 总线锁（数据很大，缓存不了）
### 乱序问题
CPU为了提高指令执行效率，会在一条指令执行过程中（比如去内存读数据（慢100倍）），去同时执行另一条指令，前提是，两条指令没有依赖关系。
- [合并写技术](https://www.cnblogs.com/liushaodong/p/4777308.html)  
WCBuffer：比L1、L2还快；CPU中只有4个字节。
#### as-if-serial
所有的动作(Action)都可以为了优化而被重排序，但是必须保证它们重排序后的结果和程序代码本身的应有结果是一致的。Java编译器、运行时和处理器都会保证单线程下的as-if-serial语义。
> 为了保证这一语义，重排序不会发生在有数据依赖的操作之中。
### 如何保障有序性
#### 1.硬件级别
- 原子指令，如x86上的"lock …" 指令是一个Full Barrier，执行时会锁住内存子系统来确保执行顺序，甚至跨多个CPU。Software Locks通常使用了内存屏障或原子指令来实现变量可见性和保持程序顺序。
- 硬件CPU内存屏障（X86）：在两条指令直接加内存屏障。
    1. sfence: store | 在sfence指令前的写操作当必须在sfence指令后的写操作前完成。
    2. lfence：load | 在lfence指令前的读操作当必须在lfence指令后的读操作前完成。
    3. mfence：mix | 在mfence指令前的读写操作当必须在mfence指令后的读写操作前完成。
- 内存屏障作用
    1. 阻止屏障两侧的指令重排序。
    2. 强制把写缓冲区/高速缓存中的脏数据等写回主内存，让缓存中相应的数据失效。
    > 在指令前插入Load Barrier，可以让高速缓存中的数据失效，强制从新从主内存加载数据。  
    > 在指令后插入Store Barrier，能让写入缓存中的最新数据更新写入主内存，让其他线程可见。

#### 2.JVM规范（JSR133）
- LoadLoad屏障  
语句：Load1; LoadLoad; Load2  
在Load2及后续读取操作要读取的数据被访问前，保证Load1要读取的数据被读取完毕。
- StoreStore屏障  
语句：Store1; StoreStore; Store2  
在Store2及后续写入操作执行前，保证Store1的写入操作对其它处理器可见。
- LoadStore屏障  
语句：Load1; LoadStore; Store2  
在Store2及后续写入操作被刷出前，保证Load1要读取的数据被读取完毕。
- StoreLoad屏障  
语句：Store1; StoreLoad; Load2  
在Load2及后续所有读取操作执行前，保证Store1的写入对所有处理器可见。  
它的开销是四种屏障中最大的。在大多数处理器的实现中，这个屏障是个万能屏障，兼具其它三种内存屏障的功能。
1. volatile
    1. 字节码层面：ACC_VOLATILE
    2. JVM层面：volatile内存区的读写都加屏障
       ``` 
       StoreStoreBarrier  
       volatile 写操作  
       StoreLoadBarrier
    
       LoadLoadBarrier  
       volatile 读操作  
       LoadStoreBarrier  
       ```
    3. [OS和硬件层面](https://blog.csdn.net/qq_26222859/article/details/52235930)
   hsdis: HotSpot Dis Assembler  
   windows: lock 指令实现
2. synchronized
    1. 字节码层面：ACC_SYNCHRONIZED 和 monitorenter monitorexit
    2. JVM层面：C/C++ 调用了操作系统提供的同步机制
    3. [OS和硬件层面](https://blog.csdn.net/21aspnet/article/details/88571740)  
       x86: lock cmpxchg / xxx
       
### 对象
1. 创建过程
    1. class loading
    2. class linking
    3. class initializing（`<cinit>` 静态代码块）
    4. 申请对象内存
    5. 成员变量赋默认值
    6. 调用构造方法`<init>`
	    1. 成员变量顺序赋初始值
	    2. 执行构造方法语句
2. 对象内存大小
- 普通对象
    1. 对象头
        1. markword：8字节
        2. ClassPointer指针：-XX:+UseCompressedClassPointers 为4字节；不开启为8字节。
    2. 实例数据：
        1. 引用类型：-XX:+UseCompressedOops 为4字节；不开启为8字节。
        2. Oops Ordinary Object Pointers
    3. Padding对齐：8的倍数
- 数组对象
    1. 对象头
        1. 同上
        2. 同上
        3. 数据长度：4字节
    2. 数组数据
    3. 同上
3. [对象怎么定位](https://blog.csdn.net/clover_lily/article/details/80095580)
    1. 句柄池
    2. 直接指针
4. 对象分配
5. GC