package com.ljt.study.ee.rmi;

import java.rmi.Naming;

/**
 * @author LiJingTang
 * @date 2020-01-04 18:02
 */
public class Client {

    public static void main(String[] args) throws Exception {
        Service service = (Service) Naming.lookup("rmi://localhost:8888/RService");
        System.out.println(service.helloworld());
        System.out.println(service.say("培培"));
    }

}
