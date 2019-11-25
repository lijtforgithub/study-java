package com.ljt.study.lang.type.enums;

/**
 * 交通信号灯枚举类
 *
 * @author LiJingTang
 * @date 2019-11-22 23:24
 */
public enum TrafficLampEnum {

    RED(30) {
        @Override
        public TrafficLampEnum nextLamp() {
            return GREEN;
        }
    },
    GREEN(45) {
        @Override
        public TrafficLampEnum nextLamp() {
            return YELLOW;
        }
    },
    YELLOW(5) {
        @Override
        public TrafficLampEnum nextLamp() {
            return RED;
        }

        // public abstract void printTime();
    };

    private int time;

    private TrafficLampEnum(int time) {
        this.time = time;
    }

    public abstract TrafficLampEnum nextLamp();

}
