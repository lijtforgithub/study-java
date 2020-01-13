## volatile
#### 可见性
指一条线程修改了这个变量的值，新值对于其他线程来说是可以立即得知的。  
不能保证原子性。如果需要采用synchronized、Lock、AtomicInteger。运用场景：
1. 运算结果并不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值
2. 变量不需要与其他的状态变量共同参与不变约束
```
volatile boolean shutdownRequested;

public void shutdown() {
    shutdownRequested = true
}

public void doWork() {
    while (!shutdownRequested) {
        // do stuff
    }
}
```
当shutdown() 方法被调用时，能保证所有线程中执行的 doWork() 方法都立即停下来
#### 有序性 | 禁止指令重排优化
普通的变量仅仅会保证在该方法的执行过程中所有依赖赋值结果的地方都能获取到正确的结果，而不能保证变量赋值操作的顺序与程序代码中执行顺序一致。  
因为在一个线程的方法执行过程中无法感知到这点。线程内表现未串行的语义。
```
char[] configText;
// 此变量必须定义为 volatile
volatile boolean initialized = false;

// 假设以下代码在线程 A 中执行。模拟读取配置信息；读取完成后将 initialized 设置为 true 以通知其他线程配置可用
configText = readConfigFile();
initialized = true;

// 假设以下代码在线程 B 中执行

while (!initialized) {
    sleep();
}

// 使用线程 A 中初始化好的配置信息
doSomethingWithConfig();
```
如果没有 volatile 修饰，就可能会由于指令重排优化，导致线程 A 中最后一句代码 initialized = true 被提前执行，这样在线程 B 中使用配置信息的代码就可能出错。
```
/**
 * 双重检锁
 */
import java.util.Objects;
 
public class DCLSingleton {

    /**
     * volatile 是必须的保持可见性和禁止指令重排
     */
    private static volatile DCLSingleton instance = null;

    private DCLSingleton() { }

    public static DCLSingleton getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (DCLSingleton.class) {
                if (Objects.isNull(instance)) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
}
```
双重检索必须加 volatile 如果没有，高并发下就会出问题。因为 instance = new DCLSingleton() 有3个步骤：
1. 分配内存
2. 初始化对象
3. 设置变量指向刚分配的地址

编译器运行时，可能会出现重排序 从1-2-3 排序为1-3-2
有2个线程 A 和 B；线程 A 在执行 instance = new DCLSingleton()，B 线程进来，而此时 A 执行了1和3，没有执行2，此时B线程判断instance不为null 直接返回一个未初始化的对象，就会出现问题。

> volatile 的读性能消耗与普通变量几乎相同，但是写操作稍慢，因为它需要在本地代码中插入许多内存屏障指令来保证处理器不发生乱序执行。
## CAS
compare and swap 比较并交换。  
CAS 操作包含三个操作数 —— 内存位置（V）、预期原值（A）和新值(B)。 如果内存位置的值与预期原值相匹配，那么处理器会自动将该位置值更新为新值 。否则，处理器不做任何操作。  
利用CPU的CAS指令，同时借助JNI来完成Java的非阻塞算法。其它原子操作都是利用类似的特性完成的。而整个J.U.C都是建立在CAS之上的，因此对于synchronized阻塞算法，J.U.C在性能上有了很大的提升。  
CAS 就是乐观锁，每次不加锁而是假设没有冲突而去完成某项操作，如果因为冲突失败就重试，直到成功为止。（synchronized是悲观锁）
#### ABA
因为CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它的值没有发生变化，但是实际上却变化了。ABA问题的解决思路就是使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一，那么A－B－A 就会变成1A-2B－3A。  
Java1.5开始JDK的atomic包里提供了一个类AtomicStampedReference来解决ABA问题。这个类的compareAndSet方法作用是首先检查当前引用是否等于预期引用，并且当前标志是否等于预期标志，如果全部相等，则以原子方式将该引用和该标志的值设置为给定的更新值。  
AtomicInteger虽然存在ABA问题，但是改变的是值，不影响。
#### 循环时间长自旋消耗资源
多个线程争夺同一个资源时，如果自旋一直不成功，将会一直占用CPU。如果JVM能支持处理器提供的pause指令那么效率会有一定的提升，pause指令有两个作用，第一它可以延迟流水线执行指令，使CPU不会消耗过多的执行资源，延迟的时间取决于具体实现的版本，在一些处理器上延迟时间是零。第二它可以避免在退出循环的时候因内存顺序冲突而引起CPU流水线被清空，从而提高CPU的执行效率。  
破坏掉for死循环，当超过一定时间或者一定次数时，return退出。  
JDK8新增的LongAddr和ConcurrentHashMap类似的方法。当多个线程竞争时，将粒度变小，将一个变量拆分为多个变量，达到多个线程访问多个资源的效果，最后再调用sum把它合起来。虽然base和cells都是volatile修饰的，但感觉这个sum操作没有加锁，可能sum的结果不是那么精确。
```
public long sum() {
    Cell[] as = cells; Cell a;
    long sum = base;
    if (as != null) {
        for (int i = 0; i < as.length; ++i) {
            if ((a = as[i]) != null)
                sum += a.value;
        }
    }
    return sum;
}
```
#### 只能保证一个共享变量的原子操作
当对一个共享变量执行操作时，我们可以使用循环CAS的方式来保证原子操作，但是对多个共享变量操作时，循环CAS就无法保证操作的原子性，这个时候就可以用锁，或者有一个取巧的办法，就是把多个共享变量合并成一个共享变量来操作。从Java1.5开始JDK提供了AtomicReference类来保证引用对象之间的原子性，你可以把多个变量放在一个对象里来进行CAS操作。