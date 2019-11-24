package com.ljt.study.lang.type.enums;

import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

/**
 * @author LiJingTang
 * @date 2019-11-23 00:02
 */
public class EnumTest {

    @Test
    public void testEnumMap() {
        Map<PayrollDayEnum, PhaseEnum> map = new EnumMap<>(PayrollDayEnum.class);
        map.put(PayrollDayEnum.MONDAY, PhaseEnum.GAS);
        System.out.println(map);
        map.put(PayrollDayEnum.TUESDAY, PhaseEnum.LIQUID);
        System.out.println(map.containsKey(PayrollDayEnum.FRIDAY));
        System.out.println(map.keySet());
        System.out.println(map.entrySet());
        System.out.println(map.values());
    }

    @Test
    public void testEnumSet() {
        EnumSet<PayrollDayEnum> enumSet1 = EnumSet.allOf(PayrollDayEnum.class);
        EnumSet<PayrollDayEnum> enumSet2 = EnumSet.noneOf(PayrollDayEnum.class);
        System.out.println(enumSet1.hashCode() + " = " + enumSet1);
        System.out.println(enumSet2.hashCode() + " = " + enumSet2);

        EnumSet<PayrollDayEnum> enumSet = enumSet1.clone();
        System.out.println(enumSet.hashCode() + " = " + enumSet);
        System.out.println(EnumSet.copyOf(enumSet));

        enumSet = EnumSet.of(PayrollDayEnum.MONDAY);
        System.out.println(enumSet);
        System.out.println(EnumSet.complementOf(enumSet));

        System.out.println(EnumSet.range(PayrollDayEnum.MONDAY, PayrollDayEnum.FRIDAY));
        System.out.println(EnumSet.of(PayrollDayEnum.MONDAY, PayrollDayEnum.FRIDAY));
    }

}
