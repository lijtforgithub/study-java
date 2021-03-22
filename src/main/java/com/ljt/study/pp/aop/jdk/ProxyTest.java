package com.ljt.study.pp.aop.jdk;

import com.ljt.study.pp.aop.service.BusinessService;
import com.ljt.study.pp.aop.service.BusinessServiceImpl;
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
 * @date 2019-12-13 11:17
 */
public class ProxyTest {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        ClassLoader classLoader = ProxyTest.class.getClassLoader();
        // 代理接口 否则有异常 is not an interface
        BusinessService businessService = new BusinessServiceImpl();
        // Proxy.newProxyInstance(classLoader, businessService.getClass().getInterfaces(), new LoggerInvocationHandler(businessService))
        BusinessService proxy = (BusinessService) Proxy.newProxyInstance(classLoader,
                new Class[]{BusinessService.class}, new ProxyTest.LoggerInvocationHandler(businessService));

        System.out.println(businessService);
        System.out.println(proxy);
        // hashcode一样 但是不是一个对象
        System.out.println(businessService == proxy);
        proxy.save();
    }

    @Test
    public void testCollection() {
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
        /*
         * getClass() native方法没有被代理
         */
        System.out.println(proxy.getClass().getName());
    }

    private static class LoggerInvocationHandler implements InvocationHandler {

        private Object target;
        private Set<String> methodNames;

        public LoggerInvocationHandler(Object obj) {
            this.target = obj;
            Method[] methods = target.getClass().getDeclaredMethods();
            methodNames = Stream.of(methods).map(Method::getName).collect(Collectors.toSet());
        }

        /**
         * public final class $Proxy0 extends Proxy implements BusinessService
         *
         *      public final void save() throws  {
         *         try {
         *             super.h.invoke(this, m4, (Object[])null);
         *         } catch (RuntimeException | Error var2) {
         *             throw var2;
         *         } catch (Throwable var3) {
         *             throw new UndeclaredThrowableException(var3);
         *         }
         *      }
         */

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object obj;
            // 为了过滤继承过来的toString()等方法
            if (methodNames.contains(method.getName())) {
                System.out.println(proxy.getClass());
                System.out.println(proxy);

                System.out.println(method.getName() + "() | before...");
            }
            obj = method.invoke(this.target, args);

            return obj;
        }
    }

}
