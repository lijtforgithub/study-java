package com.ljt.study.pp.aop.cglib;

import com.ljt.study.pp.aop.service.BusinessServiceImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author LiJingTang
 * @date 2019-12-13 11:19
 */
public class ProxyTest {

    public static void main(String[] args) {
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "C:\\Users\\Administrator\\Desktop");
        // 代理实现类 如果是私有静态内部类 必须显示声明无参构造方法
        BusinessServiceImpl proxy = (BusinessServiceImpl) new ProxyTest.CglibProxy().getProxyInstance();
        // 不能对final修饰的类进行代理 new CglibProxy().getProxyInstance(new String())
        System.out.println(proxy.delete());
    }

    private static class CglibProxy {

        public Object getProxyInstance() {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(BusinessServiceImpl.class);
            // 回调方法
            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                System.out.println(obj.getClass());

                Object retVal = proxy.invokeSuper(obj, args);
                System.out.println(method.getName() + "() | after...");

                return retVal;
            });
            // 创建代理对象
            return enhancer.create();
        }
    }

}
