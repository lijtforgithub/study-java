#### JDK Proxy

#### CGLIB
**封装了ASM。**  
 使用动态字节码生成技术实现AOP。原理是在运行期间目标字节码加载后，生成目标类的子类，将切面逻辑加入到子类中，所以使用Cglib实现AOP不需要基于接口。

#### ASM

#### Javassist

#### AspectJ