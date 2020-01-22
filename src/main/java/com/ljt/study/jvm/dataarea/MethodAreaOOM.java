package com.ljt.study.jvm.dataarea;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * VM args：-XX:MaxMetaspaceSize=10M
 *
 * @author LiJingTang
 * @date 2020-01-22 10:40
 */
public class MethodAreaOOM {

    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> methodProxy.invokeSuper(objects, args));
            enhancer.create();
        }
    }

    static class OOMObject {

    }

}
