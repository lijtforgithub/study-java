- 吞吐量：运行用户代代码时间/（运行用户代码时间+垃圾收集时间）
- 响应时间：STW越短，响应时间越好

## 调优是什么
#### 1.根据需求进行JVM规划和预调优
- 依据 业务场景 | 监控 | 压测
1. 熟悉业务场景（没有最好的垃圾回收器，只有最合适的垃圾回收器）
    1. 响应时间优秀：网站/GUI/API（CMS G1 ZGC）
    2. 吞吐量优先：科学计算/数据挖掘（PS + PO）
2. 选择回收器组合
3. 计算内存需求（经验值 1.5G 16G）
4. 选定CPU（越高越好）
5. 设定年代大小、升级年龄
6. 设定日志参数
    1. -Xloggc:/opt/xxx/logs/xxx-xxx-gc-%t.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20M -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCCause
    2. 或者每天产生一个日志文件
7. 观察日志情况
> 大流量的处理方法：分而治之
#### 2.优化运行JVM运行环境（慢，卡顿）
1. 有一个50万PV的资料类网站（从磁盘提取文档到内存）原服务器32位，1.5G的堆，用户反馈网站比较缓慢，因此公司决定升级，新的服务器为64位，16G的堆内存，结果用户反馈卡顿十分严重，反而比以前效率更低了。
   1. 为什么原网站慢：很多用户浏览数据，很多数据load到内存，内存不足，频繁GC，STW长，响应时间变慢
   2. 为什么会更卡顿：内存越大，FGC时间越长
   3. 咋办：PS -> PN + CMS 或者 G1
2. 系统CPU经常100%，如何调优
   1. 找出哪个进程CPU高（top）
   2. 该进程中的哪个线程CPU高（top -Hp PID）
   3. 导出该线程的堆栈（jstack）
   4. 查找哪个方法（栈帧）消耗时间 （jstack）
   5. 工作线程占比高 | 垃圾回收线程占比高
3. 系统内存飙高，如何查找问题
   1. 导出堆内存 （jmap）
   2. 分析 （jhat/jvisualvm/mat/jprofiler ）
4. 如何监控JVM
   1. jstat/jvisualvm/jprofiler/arthas/top
#### 3.解决JVM运行过程中出现的各种问题(OOM)
