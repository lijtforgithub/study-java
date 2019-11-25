package com.ljt.study.lang.overload;

/**
 * 多态
 *
 * @author LiJingTang
 * @date 2019-11-22 16:34
 */
public class OverLoadTest {

    public static void main(String[] args) {
        Wine wine = new Beer();
        wine.method1();

        WhiteWine[] wineArray = new WhiteWine[2];
        wineArray[0] = new MaoTai();
        wineArray[1] = new JianNanChun();

        for (int i = 0; i < 2; i++) {
            System.out.println(wineArray[i].toString() + " | " + wineArray[i].drink());
        }
    }

    private static class MaoTai extends WhiteWine {

        public MaoTai() {
            setName("茅台");
        }

        /**
         * 重写父类方法，实现多态
         */
        @Override
        public String drink() {
            return "喝的是 " + getName();
        }

        /**
         * 重写toString()
         */
        @Override
        public String toString() {
            return "WhiteWine： " + getName();
        }
    }

    private static class JianNanChun extends WhiteWine {

        public JianNanChun() {
            setName("剑南春");
        }

        /**
         * 重写父类方法，实现多态
         */
        @Override
        public String drink() {
            return "喝的是 " + getName();
        }

        /**
         * 重写toString()
         */
        @Override
        public String toString() {
            return "WhiteWine： " + getName();
        }
    }

    private static class WhiteWine {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String drink() {
            return "喝的是" + getName();
        }

        @Override
        public String toString() {
            return null;
        }
    }

    private static class Wine {

        public void method1() {
            System.out.println("Wine -> method1() this = " + this.getClass().getName());
            method2(); // 等于this.method2() 所以执行的是传入的this的method2()
        }

        public void method2() {
            System.out.println("Wine -> method2() this = " + this.getClass().getName());
        }
    }

    private static class Beer extends Wine {
        /**
         * 子类重写父类方法  指向子类的父类引用调用method2时，必定是调用该方法
         */
        @Override
        public void method2() {
            System.out.println("Beer -> method2() this = " + this.getClass().getName());
        }

        /**
         * 子类重载父类方法 父类中不存在该方法，向上转型后，父类是不能引用该方法的
         */
        public void method1(String info) {
            System.out.println("Beer -> method2(String info) this = " + this.getClass().getName());
            method2();
        }
    }

}
