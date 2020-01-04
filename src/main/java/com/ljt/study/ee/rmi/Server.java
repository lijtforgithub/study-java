package com.ljt.study.ee.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author LiJingTang
 * @date 2020-01-04 18:00
 */
public class Server {

    public static void main(String[] args) throws Exception {
        Service service = new ServiceImpl();
        LocateRegistry.createRegistry(8888);
        Naming.bind("rmi://localhost:8888/RService", service);
        System.out.println("服务端绑定Service服务成功");
    }

}
