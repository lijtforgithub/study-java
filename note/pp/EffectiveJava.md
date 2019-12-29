## chapter 02 创建和销毁对象
#### 01 考虑用静态工厂方法代理构造器
```
public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE : Boolean.FALSE;
}
```

服务提供者框架  
服务接口(Service Interface)  
提供者注册API(Provider Registration API)  
服务访问API(Service Access API)  
服务提供者接口(Service Provider Interface)

对应JDBC来说，  
Connection就是它的服务接口，  
DriverManager.regeisterDriver是提供者注册API，  
DriverManager.getConnection是服务访问API，  
Driver就是服务提供者接口。
#### 02 遇到多个构造器参数时要考虑用构建器
#### 03 用私有构造器或者枚举类型强化Singleton属性
#### 04 通过私有构造器强化不可实例化的能力
#### 05 避免创建不必要的对象
#### 06 消除过期的对象引用
#### 07 避免使用终结方法
Java语言规范不仅不保证终结方法会被及时执行，而且根本不保证他们会被执行，只是增加被执行的机会。
## chapter 03 对于所有对象都通用的方法
#### 08 覆盖equals时请遵守通用约定
java.sql.Timestamp对java.util.Date进行了扩展，并增加了nanoseconds域。  
**Timestamp的equals实现确实违反了对称性。**  
如果Timestamp和Date对象被用于同一集合中，或者以其他方式被混合在一起，则会引起不正确的行为。  
Timestamp类有一个免责声明，告诫程序员不要混合使用Date和Timestamp对象。

对于既不是float也不是double类型的基本类型域，可以使用==操作符进行比较；  
对于对象引用域，可以递归的调用equals方法；  
对于Float域，可以使用Float.compare方法；  
对于Double域，可以使用Double.compare方法；  
对于数组域，则要把以上这些指导原则应用到每个元素上。 Arrays.equals()
#### 09 覆盖equals时总要覆盖hashCode
在每个覆盖了equals方法的类中，也必须覆盖hashCode方法。如果不这样做的话，就会违反Object.hashCode的通用约定，从而导致该类无法结合所有的基于散列的集合一起正常工作，这样的集合包括HashMap、HashSet和Hashtable  
为不相等的对象产生不相等的散列码
可以把冗余域排除在外。换句话说，如果一个域的值可以根据参与计算的其他域的值计算出来，则可以吧这样的域排除在外。必须排除equals比较计算中没有用到的任何域。
#### 10 始终要覆盖toString
#### 11 谨慎地覆盖clone
#### 12 考虑实现Comparable接口
就好像违法了hashCode约定的类会破坏其他依赖于散列做法的类一样，违反compareTo约定的类也会破坏其他依赖于比较关系的类。  
依赖于比较关系的类包括有序集合类TreeSet和TreeMap，以及工具类Collections和Arrays，他们内部包含搜索和排序的方法。
## chapter 04 类和接口
#### 13 使用类和成员的可访问性最小化
#### 14 在公有类中使用访问方法而非公有域
#### 15 使可变性最小化
#### 16 复合优先于继承
不可变类只是其实例不能被修改的类。  
每个实例中包含的所有信息都必须在创建该实例的时候就提供，并在对象的整个生命周期内固定不变。  
Java平台类库中包含许多不可变的类，其中有String、基本类型包装类、BigInteger和BigDecimal。
## chapter 06 枚举在实例化时，先调用构造方法，再调用静态代码块。类在实例化时，先调用静态代码块，再调用构造方法。
## chapter 07

##
一个包的导出的API是由该包中的每个公有(public)类或者接口中所有的或者受保护的(protected)成员和构造器组成。