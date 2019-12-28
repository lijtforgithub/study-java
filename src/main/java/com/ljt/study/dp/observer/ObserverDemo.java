package com.ljt.study.dp.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author LiJingTang
 * @date 2019-12-15 20:56
 */
public class ObserverDemo {

    public static void main(String[] args) {
        MoM m = new MoM();
        Baby b = new Baby();
        b.addObserver(m);

        b.cry();
    }


    private static class Baby extends Observable {

        public Baby() {
            super();
            System.out.println("一个婴儿诞生了...");
        }

        public void cry() {
            System.out.println("婴儿哭了,要吃饭...");
            this.setChanged();
            this.notifyObservers("Hello World");
        }

        public void eat() {
            System.out.println("给婴儿喂饭吃...");
        }
    }

    private static class MoM implements Observer {

        public MoM() {
            super();
            System.out.println("妈妈看护婴儿...");
        }

        @Override
        public void update(Observable o, Object arg) {
            System.out.println(arg);

            if (o instanceof Baby) {
                Baby baby = (Baby) o;

                baby.eat();
            }
        }
    }

}
