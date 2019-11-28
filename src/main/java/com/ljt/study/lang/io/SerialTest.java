package com.ljt.study.lang.io;

import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @author LiJingTang
 * @date 2019-11-28 09:42
 */
public class SerialTest {

    private static final File FILE = new File("serialEmployee");

    @Test
    public void testSerialize() {
        Employee emp = new Employee();
        emp.setId(101);
        emp.setName("甄嬛");
        emp.setDepartment("人事部");
        Address address = new Address(1100, "西山银杏", "合肥市");
        emp.setAddress(address);

        try {
            OutputStream output = new FileOutputStream(FILE);
            ObjectOutputStream objOutput = new ObjectOutputStream(output);
            objOutput.writeObject(emp);
            objOutput.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tsetUnSerialize() {
        Employee emp = null;

        try {
            InputStream input = new FileInputStream(FILE);
            ObjectInputStream objInput;
            objInput = new ObjectInputStream(input);
            emp = (Employee) objInput.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(emp);
    }

    private static class Employee implements Serializable {

        private static final long serialVersionUID = 3158315595546860699L;

        private transient int id; // 序列化的时候忽略此字段
        private String name;
        private String department;
        private Address address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", address=" + address + "]";
        }
    }

    private static class Address implements Serializable {

        private static final long serialVersionUID = -6879826619895535994L;

        private int homeNo;
        private String street;
        private String city;

        public Address(int homeNo, String street, String city) {
            super();
            this.homeNo = homeNo;
            this.street = street;
            this.city = city;
        }

        public int getHomeNo() {
            return homeNo;
        }

        public void setHomeNo(int homeNo) {
            this.homeNo = homeNo;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return "Address [homeNo=" + homeNo + ", street=" + street + ", city=" + city + "]";
        }
    }

}
