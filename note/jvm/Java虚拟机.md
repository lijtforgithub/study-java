## Java虚拟机
#### Java：与平台无关的语言
#### JVM：与语言无关的平台
 Java语言中的各种变量、关键字和运算符号的语义最终都是由多条字节码命令组合而成的，因此字节码命令所能提供的语义描述能力肯定会比Java语言本身更加强大。因此，有一些Java语言本身无法有效支持的语言特性不代表字节码无法有效支持，这也为其他语言实现一些有别于Java的语言特性提供了基础。

## 类文件结构
class文件是一组以8位字节为基础单位的二进制流，各个数据项目严格按照顺序紧凑的排列在class文件之中，中间没有任何分隔符，这使得整个class文件中存储的内容几乎全部是程序运行的必要数据，没有空隙存在。当遇到需要占用8位字节以上空间的数据项时，则会按照高位在前的方式分割成若干个8位字节进行存储。

Java代码在进行**Javac编译**的时候，并不像C和C++那样有“连接”这一步骤，而是在虚拟机加载class文件的时候进行 **动态连接** 。也就是说，在class文件中不会保存各个方法、字段的最终内存布局信息，因此这些字段、方法的符号引用不经过运行期转换的话无法得到真正的内存入口地址，也就无法直接被虚拟机使用。当虚拟机运行时，需要从常量池获取对应的符号引用，再在类创建时或运行时解析、翻译到具体的内存地址之中。

在Java语言中，要重载（Overload）一个方法，除了要与原方法具有相同的简单名称之外，还要求必须拥有一个与原方法不同的特征签名，特征签名就是一个方法中各个参数在常量池中的字段符号引用的集合，也就是因为返回值不会包含在特征签名中，因此Java语言里面无法仅仅依靠返回值的不同来对一个已有方法进行重载的。但是在class文件格式中，特征签名的范围更大一些，只要描述符不是完全一致的两个方法也可以共存。也就是说，如果两个方法有相同的名称和特征签名，但返回值不同，那么也是可以合法共存于同一个class文件中的。

- max_stack代表了操作数栈（Operand Stacks）深度的最大值。在方法执行的任意时刻，操作数栈都不会超过这个深度。虚拟机运行的时候需要根据这个值来分配栈帧（Stack Frame）中操作栈深度。

- max_locals代表了局部变量表所需要的存储空间。在这里，max_locals的单位是变量槽（Slot），Slot是虚拟机为局部变量分配内存所使用的最小单位。对于byte、char、float、int、short、boolean和returnAddress等长度不超过32位的数据类型，每个局部变量占用1个Slot，而double和long这两种64位的数据类型则需要两个Slot来存放。局部变量表中的Slot可以重用，当局部变量作用域超出时，这个局部变量所占的Slot可以被其他局部变量使用。

- code_length，虚拟机规范中明确限制了一个方法不允许超过65535条字节码指令，超过了这个限制，Javac编译器会拒绝编译。 

## 命令
- java1.6以后默认把当前目录设置为classpath
- jar文件 = 路径(文件夹)
#### [Java](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html)
- 标准： - 开头，所有的HotSpot都支持
- 非标准：-X 开头，特定版本HotSpot支持特定命令
- 不稳定：-XX 开头，下个版本可能取消
```
-ea 指明了开启断言检测  
-cp 指明了执行这个class文件所需要的所有类的包路径-即系统类加载器的路径  
-verbose 启用详细输出
    :gc GC信息
    :class 与 java -verbose 一样（显示加载了哪些类）
-D 设置系统属性 -D<名称>=<值> eg: java -Denv=test
-X 非标准选项的帮助
```
| 参数 | 默认值 | 说明 |
|---|---|---|
| -Xms<Size> | 物理内存1/64 | 初始堆大小 |
| -Xmx<Size> | 物理内存1/4 | 最大堆大小 |
| -Xss<Size> | >=1.5 1M | 线程堆栈大小 |
| -Xmn<Size> |  | 新生代大小 |
| -Xmixed | 开启 | 混合模式执行 |
| -Xint |  | 解释模式执行（启动快/执行慢） |
| -Xcomp |  | 编译模式执行（执行快/启动慢） |
##### java -XX
```
-XX:+PrintFlagsFinal 输出所有参数的名称及默认值

-XX:+<option> 开启 option 参数
-XX:-<option> 关闭 option 参数
-XX:<option>=<value> 将 option 参数的值设为value
```
- 内存管理参数

| 参数 | 默认值 | 说明 |
|---|---|---|
| DisableExplicitGC | 关闭 | 忽略来自System.gc()方法触发的垃圾回收 |
| ExplicitGCInvokesConcurrent | 关闭 | 当收到System.gc()方法提交的垃圾收集申请时，使用CMS收集器进行收集 |
| **UseSerialGC** | Client模式开启/其他关闭 | **Client模式下的默认值**；开启后，使用Serial+Serial Old的收集器组合进行内存回收 |
| **UseParNewGC** | 关闭 | 开启后，使用ParNew+PS Old(Ps MarkSweep)的收集器组合进行内存回收 |
| **UseParallelGC** | Server模式开启/其他关闭 | 开启后，使用Parallel Scavenge+Serial Old的收集器组合进行内存回收 |
| **UseParallelOldGC** | 关闭 | **Server模式下的默认值**；开启后，使用Parallel Scavenge+Parallel Old的收集器组合进行内存回收 |
| **UseConcMarkSweepGC** | 关闭 | 开启后，使用ParNew+CMS+Serial Old的收集器组合进行内存回收，如果CMS收集器出现Concurrent Mode Failure，则Serial Old作为后备收集器 |
| SurvivorRatio | 8 | 新生代中Eden区域与Survivor区域的容量比值 |
| PretenureSizeThreshold | 0 | 直接晋升到老年代的对象大小，设置这个参数后，大于这个参数的对象将直接在老年代分配。0的意思时不管多大都是先在eden中分配内存。 |
| MaxTenuringThreshold | 15 | 0-15之间的数值。晋升到老年代的对象年龄。每个对象在坚持过一次Minor GC之后年龄+1，超过这个参数值时进入老年代 |
| UseAdaptiveSizePolicy | 开启 | 动态调整Java堆中各个区域的大小及进入老年代的年龄 |
| HandlePromotionFailure | <=1.5关闭/>=1.6开启 | 是否允许分配担保失败，即老年代的剩余空间不足以应付新生代的整个Eden和Survivor区的所有对象都存活的极端情况 |
| ParallelGCThreads | <=8默认CPU数量/>8小于CPU数量 | 设置并行GC时进行内存回收的线程数 |
| GCTimeRatio | 99 | GC时间占总时间的比率；仅在使用Parallel Scavenge收集器时生效 |
| MaxGCPauseMillis | - | 设置GC的最大停顿时间；仅在使用Parallel Scavenge收集器时生效 |
| CMSInitiatingOccupancyFraction | -1 | 设置CMS收集器在老年代空间被使用多少后触发的垃圾回收；仅在使用CMS收集器时生效 |
| UseCMSCompactAtFullCollection | 开启 | 设置CMS收集器在完成垃圾收集后是否要进行一次内存碎片整理；仅在使用CMS收集器时生效 |
| CMSFullGCsBeforeCompaction | 0 | 设置CMS收集器在进行若干次垃圾收集后再启动一次内存碎片整理；仅在使用CMS收集器时生效。0的意思是每次都整理 |
| ScavengeBeforeFullGC | 开启 | 再Full GC发生之前触发一次MinorGC |
| UseGCOverheadLimit | 开启 | 禁止GC过程无限制地执行，如果过于频繁，就直接发生OOM |
| UseTLAB | Server模式开启 | 优先在本地线程缓冲区分配对象，避免分配内存时的锁定过程 |
| MaxHeapFreeRatio | 70 | 当Xmx值比Xms值大时，堆可以动态收缩和扩展，这个参数控制当堆空闲大于指定比率时自动收缩 |
| MinHeapFreeRatio | 40 | 当Xmx值比Xms值大时，堆可以动态收缩和扩展，这个参数控制当堆空闲小于指定比率时自动扩展 |
| MaxPermSize | <=1.7 64M | 永久代最大值 |
| **MetaspaceSize** | >1.7 | 初始元空间大小 |
| **MaxMetaspaceSize** | 默认不限制 | 最大元空间大小 |
| **NewRatio** | 2 | 新生代内存容量与老生代内存容量的比例 |

- 调试参数

| 参数 | 默认值 | 说明 |
|---|---|---|
| HeapDumpOnOutOfMemoryError | 关闭 | 发生OOM时是否生成堆转储快照 |
| OnOutOfMemoryError | - | 发生OOM时执行指定的命令 |
| OnError | - | 虚拟机抛出ERROR时执行指定的命令 |
| PrintConcurrentLocks | 关闭 | 打印JUC中锁的状态 |
| PrintCommandLineFlags | 关闭 | 打印启动虚拟机时输入的非稳定参数 |
| PrintCompilation | 关闭 | 打印方法即时编译信息 |
| PrintGC | 关闭 | 打印GC信息 |
| PrintGCDetails | 关闭 | 打印GC的详细信息 |
| PrintGCDateStamps  PrintGCTimeStamps | 关闭 | 打印GC停顿耗时间 |
| PrintTenuringDistribution | 关闭 | 打印GC后新生代各个年龄对象的大小 |
| TraceClassLoading | 关闭 | 打印类加载信息 |
| TraceClassUnLoading | 关闭 | 打印类卸载载信息 |

- 即时编译参数

| 参数 | 默认值 | 说明 |
|---|---|---|
| CompileThreshold | Client模式1500/Server模式10000 | 触发方式即时编译的阈值 |
| OnStackReplacePercentage | Client模式933/Server模式140 | OSR比率，它是OSR即时编译阈值计算公式的一个参数，用于代替BackEdgeThreshold参数控制回边计算器的实际溢出阈值 |
| ReservedCodeCacheSize | - | 即时编译器编译的代码缓存值的最大值 |

- 多线程相关参数

| 参数 | 默认值 | 说明 |
|---|---|---|
| ~~UseSpinning~~ | 1.5关闭/>=1.6开启/<font color=red>>=1.7无</font> | 开启自旋锁以避免线程频繁挂起和唤醒 |
| ~~PreBlockSpin~~ | 10/<font color=red>>=1.7无</font> | 使用自旋锁时默认的自旋次数 |
| UseThreadPriorities | 开启 | 使用本地线程优先级 |
| UseBiasedLocking | 开启 | 是否使用偏向锁 |
| UseFastAccessorMethods | >=1.7关闭 | 当频繁反射执行某个方法时，生成字节码来加快反射的执行速度 |

#### jps 虚拟机进程状况
可以查看 Java 进程的启动类、传入参数和虚拟机参数等信息  
**jps -lv**
```
-q：只显示进程ID
-m：main 方法的参数，在嵌入式JVM上可能是null
-l：main class的完成package名或者应用程序的jar文件完整路径名
-v：JVM 参数
```
#### jstack Java线程堆栈跟踪
可以查看或导出 Java 应用程序中线程堆栈信息  
**jstack PID**
```
-l：长列表. 打印关于锁的附加信息，例如属于java.util.concurrent 的 ownable synchronizers列表
-F：没有相应的时候强制打印栈信息
-m：打印java和native c/c++框架的所有栈信息
```
#### jmap 内存映像
可以生成 java 程序的 dump 文件， 也可以查看堆内对象示例的统计信息、查看 ClassLoader 的信息以及 finalizer 队列  
**jmap -histo:live PID | head -20**  
**jmap -dump:format=b,file=xxx PID**
```
-histo[:live]：显示堆中对象的统计信息
-dump:<dump-options>：生成堆转储快照
    live子选项是可选的。如果指定了live子选项，堆中只有活动的对象会被转储
    heap如果比较大的话，就会导致这个过程比较耗时，并且执行的过程中为了保证dump的信息是可靠的，所以会暂停应用， 线上系统慎用
-heap：显示Java堆详细信息
-clstats：打印类加载器信息
-finalizerinfo：显示在F-Queue队列等待Finalizer线程执行finalizer方法的对象
-F：当-dump没有响应时，使用-dump或者-histo参数。在这个模式下live子参数无效
```
#### jstat 虚拟机统计信息监视
可以查看堆内存各部分的使用量，以及加载类的数量  
**jstat -gc PID**
```
-class：显示ClassLoad的相关信息
-compiler：显示JIT编译的相关信息
-gc：显示和gc相关的堆信息
-gccapacity：显示各个代的容量以及使用情况
-gcutil：显示垃圾收集信息
-gccause：显示垃圾回收的相关信息（通-gcutil）,同时显示最后一次或当前正在发生的垃圾回收的诱因；
-gcnew：显示新生代信息
-gcnewcapacity：显示新生代大小和使用情况
-gcold：显示老年代的信息
-gcoldcapacity：显示老年代的大小
-gcmetacapacity：显示元空间的信息
-printcompilation：输出JIT编译的方法信息
还可以同时加上 两个数字。如：jstat -printcompilation -h3 PID 250 6是每250毫秒打印一次，一共打印6次，-h3每三行显示标题。
```
#### jhat 虚拟机堆转储快照分析
#### jinfo Java配置信息
可以查看正在运行的 java 应用程序的扩展参数，包括Java System属性和JVM命令行参数；也可以动态的修改正在运行的 JVM 一些参数  
**jinfo PID**
**-flag UseParallelGC PID**
```
-flag name：输出对应名称的参数
-flag [+|-]name：开启或者关闭对应名称的参数
-flag name=value：设定对应名称的参数
-flags：输出全部的参数
-sysprops：输出系统属性
```
#### javap
根据class字节码文件，反解析出当前类对应的code区（汇编指令）、本地变量表、异常表和代码行偏移量映射表、常量池等等信息  
**javap -v CLASS**
```
-v 不仅会输出行号、本地变量表信息、反编译汇编代码，还会输出当前类用到的常量池等信息。
-l：会输出行号和本地变量表信息。
-c：会对当前class字节码进行反编译生成汇编代码。
```

