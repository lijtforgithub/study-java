- 吞吐量：运行用户代代码时间/（运行用户代码时间+垃圾收集时间）
- 响应时间：STW越短，响应时间越好
```
-Xloggc:/xxx/xxx-gc-%t.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=20M
    -XX:+PrintGCDetails -XX:+PrintGCDateStamps
    -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/xxx/xxx-%t.dump
```
## 调优是什么
#### 1.根据需求进行JVM规划和预调优
- 依据 业务场景 | 监控 | 压测
1. 熟悉业务场景，选择回收器组合（没有最好的垃圾回收器，只有最合适的垃圾回收器）
    1. 响应时间优秀：网站/GUI/API（CMS G1 ZGC）
    2. 吞吐量优先：科学计算/数据挖掘（PS + PO）
2. 计算内存需求（经验值 1.5G 16G）
3. 选定CPU（越高越好）
4. 设定年代大小、升级年龄
5. 设定GC日志参数
6. 观察GC日志情况
#### 2.优化运行JVM运行环境（慢，卡顿）
1. 有一个50万PV的资料类网站（从磁盘提取文档到内存）原服务器32位，1.5G的堆，用户反馈网站比较缓慢，  
因此公司决定升级，新的服务器为64位，16G的堆内存，结果用户反馈卡顿十分严重，反而比以前效率更低了。
   1. 为什么原网站慢：很多用户浏览数据，很多数据load到内存，内存不足，频繁GC，STW长，响应时间变慢
   2. 为什么会更卡顿：内存越大，FGC时间越长
   3. 咋办：PS -> PN + CMS 或者 G1
2. **CPU飙高**
   1. top 找出CPU高的进程 PID
   2. top -Hp PID 找出该进程高的线程 TID
   3. printf "%x\n" TID 线程ID转成16进制 0X-TID
   4. jstack PID > temp 导出该进程的线程堆栈信息 temp
        1. jstack PID | grep 0X-TID -A 50 堆栈信息匹配16进制线程ID后的 50 行
        2. jstack PID > temp 导出该进程的线程堆栈信息到文件 temp 然后文件里匹配0X-TID
   5. 工作线程占比高 | 垃圾回收线程占比高
3. **内存飙高**
   1. 导出堆内存 （jmap）
   2. 分析 （jhat/jvisualvm/mat/jprofiler）
4. 如何监控JVM
   1. jstat/jvisualvm/jprofiler/arthas/top
#### 3.解决JVM运行过程中出现的各种问题(OOM)
