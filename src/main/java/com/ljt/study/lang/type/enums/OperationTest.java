package com.ljt.study.lang.type.enums;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

/**
 * @author LiJingTang
 * @date 2019-11-22 23:39
 */
public class OperationTest {

    public static void main(String[] args) {
        Random random = new Random();
        double x = random.nextDouble();
        double y = random.nextDouble();

        test1(BasicOperation.class, x, y);
        test2(Arrays.asList(ExtendedOperation.values()), x, y);
    }

    private static <T extends Enum<T> & Operation> void test1(Class<T> opSet, double x, double y) {
        for (Operation op : opSet.getEnumConstants()) {
            System.out.printf("%f %s % f = %f%n", x, op, y, op.apply(x, y));
        }
    }

    private static void test2(Collection<? extends Operation> opSet, double x, double y) {
        for (Operation op : opSet) {
            System.out.printf("%f %s % f = %f%n", x, op, y, op.apply(x, y));
        }
    }

    public enum ExtendedOperation implements Operation {
        PLUS("^") {
            @Override
            public double apply(double x, double y) {
                return Math.pow(x, y);
            }
        },
        MINUS("%") {
            @Override
            public double apply(double x, double y) {
                return x % y;
            }
        };

        private final String symbol;

        private ExtendedOperation(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public enum BasicOperation implements Operation {
        PLUS("+") {
            @Override
            public double apply(double x, double y) {
                return x + y;
            }
        },
        MINUS("-") {
            @Override
            public double apply(double x, double y) {
                return x - y;
            }
        },
        TIMES("*") {
            @Override
            public double apply(double x, double y) {
                return x * y;
            }
        },
        DIVIDE("/") {
            @Override
            public double apply(double x, double y) {
                return x / y;
            }
        };

        private final String symbol;

        private BasicOperation(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }

    }

    public interface Operation {
        double apply(double x, double y);
    }

}
