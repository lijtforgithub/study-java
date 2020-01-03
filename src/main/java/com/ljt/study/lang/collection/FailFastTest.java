package com.ljt.study.lang.collection;

import com.ljt.study.juc.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author LiJingTang
 * @date 2019-11-27 20:52
 */
public class FailFastTest {

    public static void main(String[] args) {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        Thread t1 = new Thread(() -> list.forEach(i -> {
            System.out.println(Thread.currentThread().getName() + "遍历：" + i);
            ThreadUtils.sleepSeconds(1);
        }));

        Thread t2 = new Thread(() -> {
            int i = 0;
            while (i < 6) {
                System.out.println(Thread.currentThread().getName() + "run：" + i);
                if (i == 3) {
                    list.remove(i);
                }
                i++;
            }
        });

        t1.start();
        t2.start();
    }

    @Test
    public void testIterator() {
        Collection<String> collection = new ArrayList<>(3);
        collection.add("MSG-01");
        collection.add("MSG-02");
        collection.add("MSG-03");

        Iterator<String> it = collection.iterator();

        while (it.hasNext()) {
            System.out.println("enter loop...");
            String message = it.next();

            // MSG-01、MSG-02、MSG-03 对应的执行结果都不一致(与hasNext()、next()实现有关)
            if (("MSG-01").equals(message)) {
                collection.remove(message);
            }

            System.out.println(message);
        }

        System.out.println(collection.size());
    }

}
