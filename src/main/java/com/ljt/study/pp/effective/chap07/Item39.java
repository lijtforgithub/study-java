package com.ljt.study.pp.effective.chap07;

import java.util.Date;

/**
 * @author LiJingTang
 * @date 2019-12-29 11:12
 */
public class Item39 {

    /**
     * 保护性拷贝
     */
    private static final class Period {

        private final Date start;
        private final Date end;

        public Period(Date start, Date end) {
            if (start.compareTo(end) > 0) {
                throw new IllegalArgumentException(start + " after " + end);
            }

            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());
        }

        public Date getStart() {
            return new Date(start.getTime());
        }
        public Date getEnd() {
            return new Date(end.getTime());
        }
    }

}
