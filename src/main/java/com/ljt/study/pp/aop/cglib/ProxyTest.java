package com.ljt.study.pp.aop.cglib;

import com.ljt.study.pp.aop.service.BusinessServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author LiJingTang
 * @date 2019-12-13 11:19
 */
public class ProxyTest {

    public static void main(String[] args) {
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\Users\\Administrator\\Desktop");
        // 代理实现类 如果是私有静态内部类 必须显示声明无参构造方法
        BusinessServiceImpl businessService = new BusinessServiceImpl();
        System.out.println(businessService);
        BusinessServiceImpl proxy = (BusinessServiceImpl) new ProxyTest.CglibProxy().getProxyInstance(businessService);
        // 不能对final修饰的类进行代理 new CglibProxy().getProxyInstance(new String())
        System.out.println(proxy.delete());
    }

    private static class CglibProxy implements MethodInterceptor {

        public Object getProxyInstance(Object obj) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(obj.getClass());
            // 回调方法
            enhancer.setCallback(this);
            // 创建代理对象
            return enhancer.create();
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            System.out.println(obj.getClass());

            Object retVal = proxy.invokeSuper(obj, args);
            System.out.println(method.getName() + "() | after...");

            return retVal;
        }
    }

}
