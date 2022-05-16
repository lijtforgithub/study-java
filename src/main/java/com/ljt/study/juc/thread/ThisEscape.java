package com.ljt.study.juc.thread;

/**
 * this逃逸是指构造函数返回之前其他线程持有该对象的引用，this逃逸经常发生在构造函数中启动线程或注册监听器。
 *
 * @author LiJingTang
 * @date 2021-03-09 10:40
 */
class ThisEscape {

    private String value = "";

    public ThisEscape() {
        new Thread(() -> {
            // 这里是可以通过ThisEscape.this调用外围类对象的，但是测试外围累对象可能还没有构造完成， 所以会发生this逃逸现象
            System.out.println(this.value);
        }).start();
        this.value = "this escape";
    }

    public static void main(String[] args) {
        new ThisEscape();
    }

}
