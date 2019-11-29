package com.ljt.study.lang.grammar;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LiJingTang
 * @date 2019-11-29 15:55
 */
public class ProxyTest {

    @Test
    public void testCollection() throws Exception {
        Collection<String> proxy = (Collection<String>) Proxy.newProxyInstance(Collection.class.getClassLoader(),
                new Class<?>[]{Collection.class},
                new InvocationHandler() {
                    ArrayList<String> target = new ArrayList<>();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        long startTime = System.nanoTime();
                        Object retValue = method.invoke(target, args);
                        System.out.println(method.getName() + " method running time: " + (System.nanoTime() - startTime));

                        return retValue;
                    }
                });

        proxy.add("Java");
        proxy.add("Proxy");
        proxy.add("LiJingTang");
        System.out.println(proxy.size());
        System.out.println(proxy.getClass().getName()); // getClass() native方法没有被代理
    }

    @Test
    public void testJDK() {
        ClassLoader classLoader = ProxyTest.class.getClassLoader();
        // 代理接口 否则有异常 is not an interface
        BusinessService businessService = new BusinessServiceImpl();
        BusinessService proxy = (BusinessService) Proxy.newProxyInstance(classLoader,
                new Class[]{BusinessService.class}, new LoggerInvocationHandler(businessService));
//		BusinessService proxy = (BusinessService) Proxy.newProxyInstance(classLoader,
//				businessService.getClass().getInterfaces(), new LoggerInvocationHandler(businessService));

        System.out.println(businessService);
        System.out.println(proxy);
        System.out.println(businessService == proxy); // hashcode一样 但是不是一个对象
        proxy.save();
    }

    @Test
    public void testCGLIB() {
        // 代理实现类 如果是私有静态内部类 必须显示声明无参构造方法
        BusinessServiceImpl businessService = new BusinessServiceImpl();
        System.out.println(businessService);
        BusinessServiceImpl proxy = (BusinessServiceImpl) new CglibProxy().getProxyInstance(businessService);
        // 不能对final修饰的类进行代理
//        new CglibProxy().getProxyInstance(new String());

        System.out.println(proxy.delete());
    }

    private static class CglibProxy implements MethodInterceptor {

        public Object getProxyInstance(Object obj) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(obj.getClass());
            enhancer.setCallback(this);  // 回调方法
            return enhancer.create();  // 创建代理对象
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println(obj.getClass());

            Object retVal = proxy.invokeSuper(obj, args);
            System.out.println(method.getName() + "() | after...");

            return retVal;
        }
    }

    private static class LoggerInvocationHandler implements InvocationHandler {

        private Object target;
        private Set<String> methodNames;

        public LoggerInvocationHandler(Object obj) {
            this.target = obj;
            Method[] methods = target.getClass().getDeclaredMethods();
            methodNames = Stream.of(methods).map(Method::getName).collect(Collectors.toSet());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object obj = null;
            // 为了过滤继承过来的toString()等方法
            if (methodNames.contains(method.getName())) {
                System.out.println(proxy.getClass());
                System.out.println(proxy);

                System.out.println(method.getName() + "() | before...");
                obj = method.invoke(this.target, args);
            } else {
                obj = method.invoke(this.target, args);
            }

            return obj;
        }
    }

    /**
     * 这里如果是私有的 CGLIB 代理时会报 Superclass has no null constructors but no arguments were given
     */
    static class BusinessServiceImpl implements BusinessService {

        @Override
        public void save() {
            System.out.println("save()");
        }

        @Override
        public String delete() {
            System.out.println("delete()");
            return "SUCESS";
        }
    }

    private interface BusinessService {

        void save();

        String delete();
    }

}
