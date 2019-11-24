- Timer：一种定时器工具，用来在一个后台线程计划执行指定任务。可安排任务执行一次，或者定期重复执行。
- TimerTask：一个抽象类，它的子类代表一个可以被Timer计划执行的任务。
- 类Timer中，提供了四个构造方法，每个构造方法都启动了计时器线程，同时Timer类可以保证多个线程可以共享单个Timer对象而无需进行外部同步，所以Timer类是线程安全的。但是由于每一个Timer对象对应的是单个后台线程，用于顺序执行所有的计时器任务，一般情况下我们的线程任务执行所消耗的时间应该非常短，但是由于特殊情况导致某个定时器任务执行的时间太长，那么他就会独占计时器的任务执行线程，其后的所有线程都必须等待它执行完，这就会延迟后续任务的执行，使这些任务堆积在一起。
- Timer对象最后引用完成后，并且所有未处理的任务都已执行完成后，计时器的任务执行线程会正常终止（并且成为垃圾回收的对象）。但是这可能要很长时间后才发生。默认情况下，任务执行线程并不作为守护线程来运行，所以它能够阻止应用程序终止。	如果调用者想要快速终止计时器的任务执行线程，那么调用者应该调用计时器的cancel方法。
1. `schedule(TimerTask task, Date time)`  
   `schedule(TimerTask task, long delay)`  
 这两个方法，如果指定的计划执行时间scheduledExecutionTime<= systemCurrentTime，则task会被立即执行。scheduledExecutionTime不会因为某一个task的过度执行而改变。
2. `schedule(TimerTask task, Date firstTime, long period)`  
   `schedule(TimerTask task, long delay, long period)`  
 这两个方法与上面两个不同，前面提过Timer的计时器任务会因为前一个任务执行时间较长而延时。在这两个方法中，每一次执行的task的计划时间会随着前一个task实际时间而发生改变，也就是scheduledExecutionTime(n+1)=realExecutionTime(n)+periodTime。也就是说如果第n个task由于某种情况导致这次的执行时间过长，最后导致systemCurrentTime >= scheduledExecutionTime(n+1)，这时第n+1个task并不会因为到时了而执行，会等待第n个task执行完之后再执行，那么这样势必会导致n+2个的执行时间scheduledExecutionTime发生改变；
即scheduledExecutionTime(n+2)=realExecutionTime(n+1)+periodTime。所以这两个方法更加注重保存**间隔**时间的稳定。
3. `scheduleAtFixedRate(TimerTask task, Date firstTime, long period)`  
 `scheduleAtFixedRate(TimerTask task, long delay, long period)`  
scheduleAtFixedRate与schedule方法的侧重点不同，schedule方法侧重保存间隔时间的稳定，而scheduleAtFixedRate方法更加侧重于保持执行**频率**的稳定。在schedule方法中会因为前一个任务的延迟而导致其后面的定时任务延时，而scheduleAtFixedRate方法则不会， 如果第n个task执行时间过长导致systemCurrentTime >= scheduledExecutionTime(n+1)，则不会做任何等待他会立即执行第n+1个task，所以scheduleAtFixedRate方法执行时间的计算方法不同于schedule，而是scheduledExecutionTime(n)=firstExecuteTime+n*periodTime，该计算方法永远保持不变。所以scheduleAtFixedRate更加侧重于保持执行频率的稳定。

> 对于Timer的缺陷，我们可以考虑 ScheduledThreadPoolExecutor 来替代。Timer是基于绝对时间的，对系统时间比较敏感，而ScheduledThreadPoolExecutor则是基于相对时间；Timer是内部是单一线程，而ScheduledThreadPoolExecutor内部是个线程池， 所以可以支持多个任务并发执行。