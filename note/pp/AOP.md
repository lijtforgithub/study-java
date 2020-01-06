1. 静态织入  
作用于编译期，字节码加载前，性能优于动态织入。AspectJ 用一种特定语言编写切面，通过自己的语法编译工具 ajc 编译器来编译，生成一个新的代理类，该代理类增强了业务类。
2. 动态织入  
作用于运行期，字节码加载后，使用了反射，所以性能相对静态织入较差。
#### JDK动态代理
动态生成一个实现了被代理类所有接口的代理类，利用反射来调用InvocationHandler的invoke方法来代理所有类。生成代理类速度快，执行速度慢（反射）。
```
$Proxy -invoke-> InvocationHandler -invoke-> target
```
动态代理用于对接口的代理，动态产生一个实现指定接口的类，目标对象一定是要有接口的，没有接口就不能实现动态代理，只能为接口创建动态代理实例，而不能对类创建动态代理。
> sun.misc.ProxyGenerator  
> sun.misc.ProxyGenerator.saveGeneratedFiles = true 保存代理类class文件  
> Object 中的 hashCode、equals、toString 同样会被代理  
> native 方法不能被代理
#### CGLIB
**封装了ASM** 不用了解class文件格式。相比JDK生成代理类速度慢，执行速度块。  
 使用动态字节码生成技术实现AOP。原理是在运行期间目标字节码加载后，生成目标类的子类，将切面逻辑加入到子类中，所以使用Cglib实现AOP不需要基于接口。修改了字节码，所以需要依赖ASM包。
 > 不能代理private和final方法，不能代理final类。
#### ASM
ASM 是一个字节码操作的工具，静态还是动态就看在哪个阶段利用它。
> 需要了解class文件格式。
#### Javassist
Javassist需要用用字符串拼接Java源代码，稍微会比较繁琐。

## 区别
性能上各种方式的差距不算太大。考虑到易用性，在对接口进行动态代理时，使用JDK代理应该是最合适的。  
在不能使用JDK代理的情况下，可以考虑使用CGLIB或者Javassist。  
CGLIB的缺点是创建代理对象的速度慢，Javassist的缺点是需要手动编写Java源码。  
如果非要在这个两个中选择一个，那么只有在对性能要求非常高的情况下选择Javassist，其他一般情况下，个人认为CGLIB是比较合适的。
#### AspectJ
aspectJ 是编译的时候直接编译入切面，属于静态织入，原理是静态代理，速度应该是最快的。
#### Spring AOP
Spring 只是使用了与 AspectJ 一样的注解，没有使用 AspectJ 的编译器 ，转向采用动态代理技术的实现原理来构建 Spring AOP 的内部机制（动态织入），这是与 AspectJ（静态织入）最根本的区别。

## agent
提供了一种虚拟机级别支持的 AOP 实现方式。

