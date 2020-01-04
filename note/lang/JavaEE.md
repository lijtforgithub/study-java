#### JNDI
JNDI(Java Naming and Directory Interface，Java命名和目录接口)是一组在Java应用中访问命名和目录服务的API。命名服务将名称和对象联系起来，使得我们可以用名称访问对象。目录服务是一种命名服务，在这种服务里，对象不但有名称，还有属性。

TOMCAT配置jndi有全局配置和局部配置。大致的有以下三种[配置方式](http://blog.csdn.net/lgm277531070/article/details/6711177)：
```
<Resource name="jdbc/TEST" auth="Container" 
    type="javax.sql.DataSource"
    driverClassName="com.mysql.jdbc.Driver"
    url="jdbc:mysql://localhost:3306/TEST?useUnicode=true&amp;characterEncoding=UTF-8"
    username="root" password="admin"
    maxActive="100" maxIdle="30" maxWait="10000" />
```
1. 全局配置-在Tomcat的conf/context.xml配置文件中加入<Resource .../>
2. 局部配置(不推荐)-在Tomcat的server.xml的<Host><Context>标签内添加<Resource .../>
3. 局部配置-在项目的WebRoot下面的META-INF文件夹下面创建context.xml文件，加入<Resource .../>

总结：如果要配置局部的话，推荐使用第三种方式，这样不依赖tomcat了。但是还是推荐使用第一种方式好，虽然依赖tomat，但是是全局的，而且可以配置 多个。对于以后切换使用方便。在项目的web.xml中添加的资源引用可有可无。
#### JTA
JTA，即Java Transaction API，JTA允许应用程序执行分布式事务处理——在两个或多个网络计算机资源上访问并且更新数据。
- JOTM  
<Resource ... factory="org.objectweb.jotm.datasource.DataSourceFactory" />
- Tomee  
<Resource ... JtaManaged="true" />

不设置JtaManaged="true"的数据源TomEEDataSource设置之后的数据源 org.apache.openejb.resource.jdbc.managed.local.ManagedDataSource
		
\<Transaction>是Tomcat 5中的新标记，对于不支持此标记的老版本，需要使用以下语句代替事务资源的声明：
```
<Resource name="UserTransaction" auth="Container" type="javax.transaction.UserTransaction" 
 factory = "org.objectweb.jotm.UserTransactionFactory"  jotm.timeout = "60" />
```
需要注意的是，使用\<Resource>节点声明的资源默认上下文前缀"java:comp/env"，
而使用\<Transaction>节点时则是"java:comp"。  
因此，当使用4.2的方式声明用户事务时，相应的JNDI查找代码也应该改为 UserTransaction ut = (UserTransaction)initCtx.lookup("java:comp/env/UserTransaction");
#### RMI和WebSerivce
* RMI的客户端和服务端都必须是Java，WebSerivce没有这个限制。
* WebSerivce是在HTTP协议上传递XML文本文件，与语言和平台无关。
* RMI是在TCP协议上传递可序列化的Java对象，只能用在Java虚拟机上，绑定语言。
* RMI是EJB远程调用的基础，仅用RMI技术就可以实现远程调用，使用EJB是为了实现组件，事物，资源池，集群等功能。
* WebService是通过XML来传输数据，可用HTTP等协议因此可在异构系统间传递，并且可以穿过防火墙，可在公网上远程调用。
#### Servlet和WebSerivce
* Servlet:提供了请求/响应模式，是Java的一种规范，只能使用于Java上，用来替代早期使用的难懂的CGI，是一种无状态的请求响应，客户端访问一个服务器的URL，只需要发送简单的HttpRequest即可。 规定了四个范围：pageContext、request、session、application。一定依赖于各种SERVLET容器，但Servlet只能接受一个简单的HTTP请求；
* WebService最早是微软提出了一种以XML为载体网络信息传输的规范，现在几乎所有的语言与平台都支持，带有状态机制，不依赖于SERVLET容器，可以发送一个XML作为其请求内容，WebService通常是基于HTTP的远程方法调用(RMI)，号称是可以反回远程对象， 一般来说客户段可以象调用本地方法一样调用WebService的方法。
* Servlet使用HTTP协议传输数据，如果你用Servlet返回XML，那个XML的描述框架就是你定的，必须告知使用者具体的说明，没有统一标准。WebService使用固定的XML格式封装成SOAP消息，可以使用HTTP作为底层数据传输，但并不局限于HTTP协议，方法返回消息是有标准的。
* Servlet返回的是HTML页面；webservice返回的可以是复杂对象甚至使用附件或者mutidata的二进制文件。
* Servlet需要遵守J2EE的Web   Application规范部署的应用服务器上，如Tomcat,Weblogic，Websphere；WebService则需要有WSDL文件来部署服务，或者使用UDDI注册。
* WebService的跨平台特性是Servlet不能比的，可以被各种语言调用；Servlet相对来说速度上的优势也是不可忽视的。
#### RPC与RMI
* RPC 跨语言，而 RMI只支持Java。
* RMI 调用远程对象方法，允许方法返回 Java 对象以及基本数据类型，而RPC 不支持对象的概念，传送到 RPC 服务的消息由外部数据表示 (External Data Representation, XDR) 语言表示，这种语言抽象了字节序类和数据类型结构之间的差异。只有由 XDR 定义的数据类型才能被传递， 可以说 RMI是面向对象方式的 Java RPC 。
* 在方法调用上，RMI中，远程接口使每个远程方法都具有方法签名。如果一个方法在服务器上执行，但是没有相匹配的签名被添加到这个远程接口上，那么这个新方法就不能被RMI客户方所调用。在RPC中，当一个请求到达RPC服务器时，这个请求就包含了一个参数集和一个文本值，通常形成classname.methodname的形式。这就向RPC服务器表明，被请求的方法在为 classname的类中，名叫methodname。然后RPC服务器就去搜索与之相匹配的类和方法，并把它作为那种方法参数类型的输入。这里的参数类型是与RPC请求中的类型是匹配的。一旦匹配成功，这个方法就被调用了，其结果被编码后返回客户方。