package com.ljt.study.lang.api;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author LiJingTang
 * @date 2019-11-21 15:06
 */
public class BeanInfoTest {

    public static void main(String[] args) throws Exception {
        User user = new User(1, "甄嬛");
        String propertyName = "ID";
        PropertyDescriptor pd = new PropertyDescriptor(propertyName, user.getClass());
        Method method = pd.getReadMethod();
        System.out.println(method.invoke(user));

        propertyName = "name";
        pd = new PropertyDescriptor(propertyName, user.getClass());
        method = pd.getWriteMethod();
        method.invoke(user, "李敬堂");

        BeanInfo beanInfo = Introspector.getBeanInfo(user.getClass());
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propDes : pds) {
            if (propDes.getName().equals(propertyName)) {
                method = pd.getReadMethod();
                System.out.println(method.invoke(user));
            }
        }
    }

    private static class User {

        private int ID;
        private String name;

        public User(int iD, String name) {
            super();
            this.ID = iD;
            this.name = name;
        }

        public int getID() {
            return ID;
        }

        public void setID(int iD) {
            ID = iD;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
