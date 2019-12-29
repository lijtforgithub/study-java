package com.ljt.study.pp.effective.chap06;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LiJingTang
 * @date 2019-12-29 11:08
 */
public class Item30 {

    @Test
    public void testOperation() {
        Random random = new Random();
        double x = random.nextDouble();
        double y = random.nextDouble();

//		Arrays.stream(OperationEnum.values());
        Stream.of(OperationEnum.values())
                .forEach(op -> System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y)));

        System.out.println(OperationEnum.valueOf("PLUS"));
        System.out.println(OperationEnum.inverse(OperationEnum.fromString("*")));
    }

    @Test
    public void testPayrollDay() {
        System.out.println(PayrollDayEnum.SUNDAY.ordinal());

        EnumSet<PayrollDayEnum> set = EnumSet.of(PayrollDayEnum.SATURDAY, PayrollDayEnum.SUNDAY);
        System.out.println(set);
    }

    @Test
    public void testEnumMap() {
        EnumMap<OperationEnum, String> eMap = new EnumMap<>(OperationEnum.class);
        eMap.put(OperationEnum.PLUS, "加");
        eMap.put(OperationEnum.MINUS, "减");
        eMap.put(OperationEnum.TIMES, "乘");
        eMap.put(OperationEnum.DIVIDE, "除");

        System.out.println(eMap.toString());
    }

    private static enum OperationEnum {

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

        private OperationEnum(String symbol) {
            System.out.println("构造方法" + symbol);
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return this.symbol;
        }

        abstract public double apply(double x, double y);


        private static final Map<String, OperationEnum> stringToEnum = Arrays.stream(OperationEnum.values())
                .collect(Collectors.toMap(OperationEnum::toString, Function.identity()));

        static {
            System.out.println("静态块");
        }

        public static OperationEnum fromString(String symbol) {
            return stringToEnum.get(symbol);
        }

        public static OperationEnum inverse(OperationEnum op) {
            switch (op) {
                case PLUS:
                    return OperationEnum.MINUS;
                case MINUS:
                    return OperationEnum.PLUS;
                case TIMES:
                    return OperationEnum.DIVIDE;
                case DIVIDE:
                    return OperationEnum.TIMES;
                default:
                    throw new AssertionError("Unknow op: " + op);
            }
        }
    }

    private static enum PayrollDayEnum {

        MONDAY(PayTypeEnum.WEEKDAY),
        TUESDAY(PayTypeEnum.WEEKDAY),
        WEDNESDAY(PayTypeEnum.WEEKDAY),
        THURSDAY(PayTypeEnum.WEEKDAY),
        FRIDAY(PayTypeEnum.WEEKDAY),
        SATURDAY(PayTypeEnum.WEEKEND),
        SUNDAY(PayTypeEnum.WEEKEND);

        private final PayTypeEnum payType;

        private PayrollDayEnum(PayTypeEnum payType) {
            this.payType = payType;
        }

        public double pay(double hoursWorked, double payRate) {
            return this.payType.pay(hoursWorked, payRate);
        }

        /**
         * 策咯枚举
         */
        private enum PayTypeEnum {

            WEEKDAY {
                @Override
                double overtimePay(double hours, double payRate) {
                    return hours <= HOURS_PER_SHIFT ? 0 : (hours - HOURS_PER_SHIFT) * payRate / 2;
                }
            },
            WEEKEND {
                @Override
                double overtimePay(double hours, double payRate) {
                    return hours * payRate / 2;
                }
            };

            private static final int HOURS_PER_SHIFT = 8;

            abstract double overtimePay(double hours, double payRate);

            double pay(double hoursWorked, double payRate) {
                double basePay = hoursWorked * payRate;

                return basePay + overtimePay(hoursWorked, payRate);
            }
        }
    }

}
