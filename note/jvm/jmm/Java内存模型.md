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
### 如何保障有序性
#### 1.硬件级别
1. 硬件CPU内存屏障（X86）：在两条指令直接加内存屏障。
    1. sfence: store | 在sfence指令前的写操作当必须在sfence指令后的写操作前完成。
    2. lfence：load | 在lfence指令前的读操作当必须在lfence指令后的读操作前完成。
    3. mfence：mix | 在mfence指令前的读写操作当必须在mfence指令后的读写操作前完成。
2. 原子指令，如x86上的"lock …" 指令是一个Full Barrier，执行时会锁住内存子系统来确保执行顺序，甚至跨多个CPU。Software Locks通常使用了内存屏障或原子指令来实现变量可见性和保持程序顺序。
#### 2.JVM规范
