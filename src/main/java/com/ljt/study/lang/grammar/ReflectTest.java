package com.ljt.study.lang.grammar;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author LiJingTang
 * @date 2019-11-28 14:27
 */
public class ReflectTest {

    @Test
    public void testClass() throws Exception {
        String str = "java.lang.String";
        Class<?> clazz1 = str.getClass();
        Class<?> clazz2 = String.class;
        Class<?> clazz3 = Class.forName(str);

        System.out.println(clazz1 == clazz2);
        System.out.println(clazz2 == clazz3);

        System.out.println(clazz1.isPrimitive());
        System.out.println(void.class.isPrimitive());
        System.out.println(int.class.isPrimitive());
        System.out.println(Integer.class.isPrimitive());
        System.out.println(int.class == Integer.TYPE);
        System.out.println(int[].class.isPrimitive());
        System.out.println(int[].class.isArray());
    }

    @Test
    public void testConstructor() throws Exception {
        String str = "java.lang.String";
        Constructor<String> constructor = String.class.getConstructor(StringBuffer.class);
        String string = constructor.newInstance(new StringBuffer(str));
        System.out.println(string.substring(0, 4));
        String s = String.class.newInstance(); // 调用的就是类的无参构造方法
        System.out.println(s.intern());
    }

    @Test
    public void testField() throws Exception {
        Point po = new Point(3, 5);
        Field fieldY = po.getClass().getField("y");
        System.out.println(fieldY.get(po));
        Field fieldX = po.getClass().getDeclaredField("x");
        fieldX.setAccessible(true);
        System.out.println(fieldX.get(po));
    }

    private static <E> Field getField(Class<?> clazz, String fieldName) {
        Field field = null;

        for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
            try {
                field = c.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return field;
    }

    @Test
    public void testMainMethod() throws Exception {
        String[] array = {"hello", "word", "from", "java"};
        Method main = Class.forName("com.ljt.study.lang.grammar.ReflectTest$InvokeMainMethod").getMethod("main",
                String[].class);
//        main.invoke(null, array); // error
        main.invoke(null, new Object[]{array});
        main.invoke(null, (Object) array);
    }

    @Test
    public void testChangeFiledValue() throws Exception {
        Point po = new Point(3, 5);
        Field[] fields = po.getClass().getFields();

        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                if (field.getType() == String.class) { // 一个字节码
                    String oldValue = (String) field.get(po);
                    String newValue = oldValue.replace('O', 'o');
                    field.set(po, newValue);
                }
            }
        }

        System.out.println(po);
    }

    @Test
    public void testMethod() throws Exception {
        String s = "甄嬛传";
        Method method = s.getClass().getMethod("charAt", int.class);
        System.out.println(method.invoke(s, 1));

        Point po = new Point(3, 5);
//        Method privateMethod = po.getClass().getMethod("privateMethod");
//        System.out.println(privateMethod.invoke(po));

        Method staticMethod = po.getClass().getMethod("staticMethod");
        System.out.println(staticMethod.invoke(null));
    }

    @Test
    public void testReflectArray() {
        int[] array1 = new int[3];
        int[] array2 = new int[4];
        int[][] array3 = new int[3][1];
        String[] array4 = new String[3];

        System.out.println(array1.getClass() == array2.getClass()); // 元素类型相同维数相同的数组共享一份字节码
        // System.out.println(array1.getClass() == array3.getClass());
        System.out.println(array1.getClass().getName());
        System.out.println(array1.getClass().getSuperclass());
        System.out.println(array4.getClass().getSuperclass());

        Object obj1 = array1;
        Object obj2 = array4;
//        Object[] obj3 = array1; // int不能转换为Object
        Object[] obj4 = array3; // int[]可以转换为Object
        Object[] obj5 = array4;

        int[] iArray = new int[]{1, 2, 3, 4, 5};
        String[] sArray = new String[]{"hello", "world", "from", "java"};
        System.out.println(Arrays.asList(iArray));
        System.out.println(Arrays.asList(sArray));

        printObject(sArray);
    }

    private static void printObject(Object obj) {
        Class<?> clazz = obj.getClass();

        if (clazz.isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++)
                System.out.print(i == 0 ? Array.get(obj, i) : " " + Array.get(obj, i));
        } else
            System.out.println(obj);
    }


    private static class InvokeMainMethod {

        public static void main(String[] args) {
            for (int i = 0; i < args.length; i++)
                System.out.print(i == 0 ? args[i] : " " + args[i]);
            System.out.println();
        }
    }

    private static class Point {

        private int x;
        public int y;

        public String hello = "HELLO";
        public String world = "WORLD";
        public String java = "JAVA";

        public Point(int x, int y) {
            super();
            this.x = x;
            this.y = y;
        }

        private String privateMethod() {
            return "privateMethod";
        }

        public static String staticMethod() {
            return "staticMethod";
        }

        @Override
        public String toString() {
            return "Point [x=" + x + ", y=" + y + ", hello=" + hello + ", world=" + world + ", java=" + java + "]";
        }
    }

}
