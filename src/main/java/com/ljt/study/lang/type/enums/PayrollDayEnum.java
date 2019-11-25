package com.ljt.study.lang.type.enums;

/**
 * 策略枚举
 *
 * @author LiJingTang
 * @date 2019-11-22 23:50
 */
public enum PayrollDayEnum {

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
        // 可以访问内部类的私有方法
        return payType.pay(hoursWorked, payRate);
    }

    private enum PayTypeEnum {
        WEEKDAY {
            @Override
            double overtimePay(double hrs, double payRate) {
                return hrs <= HOURS_PER_SHIFT ? 0 : (hrs - HOURS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            @Override
            double overtimePay(double hrs, double payRate) {
                return hrs * payRate / 2;
            }
        };

        private static final int HOURS_PER_SHIFT = 8;

        abstract double overtimePay(double hrs, double payRate);

        private double pay(double hoursWorked, double payRate) {
            double basePay = hoursWorked * payRate;
            return basePay + overtimePay(hoursWorked, payRate);
        }
    }

}
