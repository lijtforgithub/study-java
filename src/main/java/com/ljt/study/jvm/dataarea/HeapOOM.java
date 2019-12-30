package com.ljt.study.jvm.dataarea;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆OOM
 * VM Args: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 *
 * @author LiJingTang
 * @date 2019-12-30 11:17
 */
public class HeapOOM {

    public static void main(String[] args) {

        List<OOMObject> list = new ArrayList<>();

        while (true) {
            list.add(new OOMObject());
        }

    }

    private static class OOMObject {

    }

}
