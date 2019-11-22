package com.ljt.study.lang.api;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author LiJingTang
 * @date 2019-11-21 14:45
 */
public class ObjectTest {

    @Test
    public void testMethod() {
        ObjectTest obj = new ObjectTest();

        System.out.println("哈希码：" + obj.hashCode());
    }

    /**
     * 如果此对象的类不能实现接口 Cloneable，则会抛出 CloneNotSupportedException。所有的数组都被视为实现接口 Cloneable。
     */
    @Test
    public void testClone() throws CloneNotSupportedException {
        Object obj = new Object();
        // obj.clone(); // Object 类本身不实现接口 Cloneable，所以在类为 Object 的对象上调用 clone 方法将会导致在运行时抛出异常。

        ObjectTest objTest = new ObjectTest();
        try {
            objTest.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("ObjectTest没有实现接口 Cloneable导致在运行时抛出异常CloneNotSupportedException");
        }

        MyObject myObj1 = new MyObject();
        myObj1.setObj(obj);
        MyObject myObj2 = (MyObject) myObj1.clone();

        System.out.println("myObj1: " + myObj1 + " - myObj2: " + myObj2);
        System.out.println(myObj1 != myObj2);
        System.out.println(myObj1.equals(myObj2));
        System.out.println(myObj1.getClass() == myObj2.getClass());
        System.out.println("浅复制（没有替换成员对象引用）: " + (myObj1.getObj() == myObj2.getObj()));
        System.out.println("在 super.clone返回对象之前，有必要对该对象的一个或多个字段进行修改 : " + (myObj1.getFile() == myObj2.getFile()));
    }

    @Test
    public void testFinalize() {
        MyObject obj = new MyObject();

        try {
            obj.finalize(); // 当垃圾回收器确定不存在对该对象的更多引用时，由对象的垃圾回收器调用此方法。
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static class MyObject implements Cloneable {

        private Object obj;
        private File file;

        public MyObject cloneObject() {
            try {
                MyObject MyObj = (MyObject) this.clone();
                MyObj.setFile(new File(File.separator));

                return MyObj;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            MyObject MyObj = (MyObject) super.clone();
            MyObj.setFile(new File(File.separator));

            return MyObj;
        }

        @Override
        public void finalize() throws Throwable {
            super.finalize();
        }

        public Object getObj() {
            return obj;
        }
        public void setObj(Object obj) {
            this.obj = obj;
        }
        public File getFile() {
            return file;
        }
        public void setFile(File file) {
            this.file = file;
        }
    }

}
