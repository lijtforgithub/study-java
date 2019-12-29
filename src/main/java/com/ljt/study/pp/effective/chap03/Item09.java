package com.ljt.study.pp.effective.chap03;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiJingTang
 * @date 2019-12-28 22:16
 */
public class Item09 {

    public static void main(String[] args) {
        Map<PhoneNumber, String> map = new HashMap<>(1);
        map.put(new PhoneNumber(707, 867, 5309), "Jenny");
        System.out.println(map.get(new PhoneNumber(707, 867, 5309)));
    }

    private static class PhoneNumber implements Comparable<PhoneNumber> {

        private final short areaCode;
        private final short prefix;
        private final short lineNumber;

        public PhoneNumber(int areaCode, int prefix, int lineNumber) {
            super();
            rangeCheck(areaCode, 999, "area code");
            rangeCheck(prefix, 999, "prefix");
            rangeCheck(lineNumber, 9999, "line number");
            this.areaCode = (short) areaCode;
            this.prefix = (short) prefix;
            this.lineNumber = (short) lineNumber;
        }

        private static void rangeCheck(int arg, int max, String name) {
            if (arg < 0 || arg > max) {
                throw new IllegalArgumentException(name + ": " + arg);
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PhoneNumber)) {
                return false;
            }
            PhoneNumber pn = (PhoneNumber) obj;

            return pn.lineNumber == this.lineNumber && pn.prefix == this.prefix && pn.areaCode == this.areaCode;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + areaCode;
            result = 31 * result + prefix;
            result = 31 * result + lineNumber;

            return result;
        }

        @Override
        public String toString() {
            return String.format("(%03d) %03d-%04d", areaCode, prefix, lineNumber);
        }

        @Override
        public int compareTo(PhoneNumber pn) {
            int areaCodeDiff = areaCode - pn.areaCode;
            if (0 != areaCodeDiff) {
                return areaCodeDiff;
            }

            int prefixDiff = prefix - pn.prefix;
            if (0 != prefixDiff) {
                return prefixDiff;
            }

            return lineNumber - pn.lineNumber;
        }
    }

}
