package com.ljt.study.lang.generic;

/**
 * 泛型匹配规则
 *
 * @author LiJingTang
 * @date 2019-11-23 20:17
 */
class GenericRule {

    public static void main(String[] args) {
        GenericClass<Integer> intStack = new GenericClass<>();
        intStack.push(1);
        intStack.push(2);
        intStack.push(-2);

        GenericClass<Object> objectStack = new GenericClass<>();
        objectStack.push("Sun");
        objectStack.push("Java");

        // 尽管Integer是Number的子类型，但是，GenericClass<Integer>GenericClass<Number>的子类型
        // System.out.println("The max number is " + max(intStack));
        System.out.println("The max number is " + WildcardGeneric.maxOfBoundedWildcard(intStack));
        WildcardGeneric.print(objectStack);

        WildcardGeneric.add(intStack, objectStack);
        WildcardGeneric.print(objectStack);
    }

    /**
     * 通配泛型
     */
    private static class WildcardGeneric {

        private static double max(GenericClass<Number> stack) {
            double max = stack.pop().doubleValue();

            while (!stack.isEmpty()) {
                double value = stack.pop().doubleValue();
                if (value > max) {
                    max = value;
                }
            }

            return max;
        }

        private static double maxOfBoundedWildcard(GenericClass<? extends Number> stack) {
            double max = stack.pop().doubleValue();

            while (!stack.isEmpty()) {
                double value = stack.pop().doubleValue();
                if (value > max) {
                    max = value;
                }
            }

            return max;
        }

        private static void print(GenericClass<?> stack) {
            while (!stack.isEmpty()) {
                System.out.print(stack.pop() + " ");
            }
        }

        private static <T> void add(GenericClass<T> stack1, GenericClass<? super T> stack2) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
    }

    /**
     * 受限泛型
     */
    private static class BoundedGeneric {

        private static <E extends Clazz> boolean isEquals(E o1, E o2) {
            return o1.getSize() == o2.getSize();
        }

        private class Clazz {
            private int size;

            public int getSize() {
                return size;
            }
        }
    }

}