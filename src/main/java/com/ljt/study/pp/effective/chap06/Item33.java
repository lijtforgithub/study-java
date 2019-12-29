package com.ljt.study.pp.effective.chap06;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author LiJingTang
 * @date 2019-12-29 11:11
 */
public class Item33 {

    private static enum PhaseEnum {
        SOLID, LIQUID, GAS;

        public enum Transition {
            MELT(SOLID, LIQUID),
            FREEZE(LIQUID, SOLID),
            BOIL(LIQUID, GAS),
            CONDENSE(GAS, LIQUID),
            SUBLIME(SOLID, GAS),
            DEPOSIT(GAS, SOLID);

            private final PhaseEnum src;
            private final PhaseEnum dst;

            private Transition(PhaseEnum src, PhaseEnum dst) {
                this.src = src;
                this.dst = dst;
            }

            private static final Map<PhaseEnum, Map<PhaseEnum, Transition>> map = new EnumMap<PhaseEnum, Map<PhaseEnum, Transition>>(PhaseEnum.class);

            static {
                for (PhaseEnum p : PhaseEnum.values()) {
                    map.put(p, new EnumMap<PhaseEnum, Transition>(PhaseEnum.class));
                }
                for (Transition t : Transition.values()) {
                    map.get(t.src).put(t.dst, t);
                }
            }

            public static Transition from(PhaseEnum src, PhaseEnum dst) {
                return map.get(src).get(dst);
            }
        }
    }

}
