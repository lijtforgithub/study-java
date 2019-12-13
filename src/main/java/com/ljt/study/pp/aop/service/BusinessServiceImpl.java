package com.ljt.study.pp.aop.service;

/**
 * 如果是私有的 CGLIB 代理时会报 Superclass has no null constructors but no arguments were given
 *
 * @author LiJingTang
 * @date 2019-12-13 11:15
 */
public class BusinessServiceImpl implements BusinessService {

    @Override
    public void save() {
        System.out.println("save()");
    }

    @Override
    public String delete() {
        System.out.println("delete()");
        return "OK";
    }

}
