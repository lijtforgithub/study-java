package com.ljt.study.lang.collection;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;

/**
 * @author LiJingTang
 * @date 2019-11-27 20:37
 */
public class QueueTest {

    @Test
    public void testPriorityQueue() {
        PriorityQueue<GregorianCalendar> pq = new PriorityQueue<>();
        pq.add(new GregorianCalendar(1906, Calendar.DECEMBER, 9));
        pq.add(new GregorianCalendar(1815, Calendar.DECEMBER, 10));
        pq.add(new GregorianCalendar(1903, Calendar.DECEMBER, 3));
        pq.add(new GregorianCalendar(1910, Calendar.JUNE, 22));

        for (GregorianCalendar date : pq) {
            System.out.println(date.get(Calendar.YEAR));
        }

        System.out.println();

        while (!pq.isEmpty()) {
            System.out.println(pq.remove().get(Calendar.YEAR));
        }
    }

}
