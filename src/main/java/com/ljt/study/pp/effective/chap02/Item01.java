package com.ljt.study.pp.effective.chap02;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LiJingTang
 * @date 2019-12-28 21:00
 */
public class Item01 {

    private static class Services {

        private static final Map<String, Provider> providerMap = new ConcurrentHashMap<>();
        private static final String DEFAULT_PROVIDER_NAME = "<def>";

        private Services() {
        }

        /**
         * 提供者注册API
         */
        public static void registerDefaultProvider(Provider provider) {
            registerProvider(DEFAULT_PROVIDER_NAME, provider);
        }

        public static void registerProvider(String name, Provider provider) {
            providerMap.put(name, provider);
        }

        /**
         * 服务访问API
         */
        public static Service newInstance() {
            return newInstance(DEFAULT_PROVIDER_NAME);
        }

        public static Service newInstance(String name) {
            Provider provider = providerMap.get(name);

            if (null == provider) {
                throw new IllegalArgumentException("No provider registered with name " + name);
            }

            return provider.newService();
        }
    }

    /**
     * 服务提供者接口
     */
    private interface Provider {

        Service newService();

    }

    /**
     * 服务接口
     */
    private interface Service {

    }

}
