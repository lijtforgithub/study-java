package com.ljt.study.lang.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author LiJingTang
 * @date 2019-11-28 09:42
 */
class SerialTest {

    private static final File FILE = new File("jdk_serial");

    @SneakyThrows
    @Test
    void serialize() {
        Employee emp = new Employee().setId(100).setName("甄嬛").setDepartment("人事部")
                .setAddress(new Address(1100, "西山银杏", "合肥市"));
        emp.setSuperName("父类实现Serializable接口才会序列化");

        try (OutputStream output = new FileOutputStream(FILE);
             ObjectOutputStream objOutput = new ObjectOutputStream(output)) {
            objOutput.writeObject(emp);
        }
    }

    @SneakyThrows
    @Test
    void unSerialize() {
        try (InputStream input = new FileInputStream(FILE);
             ObjectInputStream objInput = new ObjectInputStream(input)) {
            Employee emp = (Employee) objInput.readObject();
            System.out.println(emp);
//            System.out.println("静态属性：" + emp.realName);
            System.out.println("父类属性：" + emp.getSuperName());
        }
    }

    @SneakyThrows
    @Test
    void externalizable() {
        User user = new User().setId(100).setName("璟瑜").setPassword("123456");
        user.setSuperName("自定义序列化属性");
        try (OutputStream output = new FileOutputStream(FILE);
             ObjectOutputStream objOutput = new ObjectOutputStream(output)) {
            objOutput.writeObject(user);
        }

        try (InputStream input = new FileInputStream(FILE);
             ObjectInputStream objInput = new ObjectInputStream(input)) {
            user = (User) objInput.readObject();
            System.out.println(user);
            System.out.println("父类属性：" + user.getSuperName());
        }
    }


    @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    @Data
    private static class Employee extends Person implements Serializable {

        /**
         * 如果此值变化 反序列化会失败
         */
        private static final long serialVersionUID = 1L;

        /**
         * 静态字段也不会序列化 类的属性
         */
        static String realName = "静态属性不会被序列化";
        /**
         * 序列化的时候忽略transient修饰的字段
         */
        private transient int id;
        private String name;
        private String department;
        private Address address;

    }

    @Accessors(chain = true)
    @AllArgsConstructor
    @Data
    private static class Address implements Serializable {

        private static final long serialVersionUID = 1L;

        private int homeNo;
        private String street;
        private String city;

    }

    @Data
    private static class Person {

        private String superName;

    }

    /**
     * 自定义序列化属性和反序列化属性
     */
    @Accessors(chain = true)
    @Data
    private static class User extends Person implements Externalizable {

        private static final long serialVersionUID = 1L;

        static String realName = "自定义序列化属性";

        private transient int id;
        private String name;
        private String password;

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(id);
            out.writeObject(name);
            out.writeObject(realName);
            out.writeObject(super.getSuperName());
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            id = (int) in.readObject();
            name = (String) in.readObject();
            realName = (String) in.readObject();
            String superName = (String) in.readObject();
            setSuperName(superName);
        }

    }

}
