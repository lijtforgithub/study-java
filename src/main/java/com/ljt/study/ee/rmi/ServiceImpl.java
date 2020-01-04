package com.ljt.study.ee.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author LiJingTang
 * @date 2020-01-04 18:03
 */
public class ServiceImpl extends UnicastRemoteObject implements Service {

    private static final long serialVersionUID = 5787919934128900864L;

    protected ServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String helloworld() throws RemoteException {
        return "Hello World";
    }

    @Override
    public String say(String name) throws RemoteException {
        return "Hello " + name;
    }

}
