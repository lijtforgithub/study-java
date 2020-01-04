package com.ljt.study.ee.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author LiJingTang
 * @date 2020-01-04 18:02
 */
public interface Service extends Remote {

    String helloworld() throws RemoteException;

    String say(String name) throws RemoteException;

}
