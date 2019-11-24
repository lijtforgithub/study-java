package com.ljt.study.lang.type.enums;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author LiJingTang
 * @date 2019-11-22 23:59
 */
public enum PhaseEnum {

    SOLID, LIQUID, GAS;

    public enum TransitionEnum {
        MELT(SOLID, LIQUID),
        SUBLIME(SOLID, GAS),
        FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS),
        CONDENSE(GAS, LIQUID),
        DEPOSIT(GAS, SOLID);

        private final PhaseEnum src;
        private final PhaseEnum dest;

        private TransitionEnum(PhaseEnum src, PhaseEnum dest) {
            this.src = src;
            this.dest = dest;
        }

        private static final Map<PhaseEnum, Map<PhaseEnum, TransitionEnum>> m = new EnumMap<>(PhaseEnum.class);

        static {
            for (PhaseEnum p : PhaseEnum.values()) {
                m.put(p, new EnumMap<PhaseEnum, TransitionEnum>(PhaseEnum.class));
            }
            for (TransitionEnum trans : TransitionEnum.values()) {
                m.get(trans.src).put(trans.dest, trans);
            }
        }

        public static TransitionEnum from(PhaseEnum src, PhaseEnum dest) {
            return m.get(src).get(dest);
        }
    }

}
