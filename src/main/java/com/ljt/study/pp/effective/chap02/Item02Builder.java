package com.ljt.study.pp.effective.chap02;

/**
 * @author LiJingTang
 * @date 2019-12-28 21:10
 */
public class Item02Builder {

    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts
                .NutritionFactsBuilder(240, 8).calories(100).sodium(35).carbohydrate(27).build();

        System.out.println(cocaCola.hashCode());
    }

    /**
     * Builder模式
     */
    private static class NutritionFacts {

        private final int servingSize;
        private final int servings;
        private final int calories;
        private final int fat;
        private final int sodium;
        private final int carbohydrate;

        private NutritionFacts(NutritionFactsBuilder builder) {
            servingSize = builder.servingSize;
            servings = builder.servings;
            calories = builder.calories;
            fat = builder.fat;
            sodium = builder.sodium;
            carbohydrate = builder.carbohydrate;
        }

        public static class NutritionFactsBuilder implements Builder<NutritionFacts> {

            private final int servingSize;
            private final int servings;
            private int calories = 0;
            private int fat = 0;
            private int sodium = 0;
            private int carbohydrate = 0;

            public NutritionFactsBuilder(int servingSize, int servings) {
                super();
                this.servingSize = servingSize;
                this.servings = servings;
            }

            public NutritionFactsBuilder calories(int val) {
                calories = val;
                return this;
            }

            public NutritionFactsBuilder fat(int val) {
                fat = val;
                return this;
            }

            public NutritionFactsBuilder sodium(int val) {
                sodium = val;
                return this;
            }

            public NutritionFactsBuilder carbohydrate(int val) {
                carbohydrate = val;
                return this;
            }

            public NutritionFacts build() {
                return new NutritionFacts(this);
            }
        }


        public int getServingSize() {
            return servingSize;
        }

        public int getServings() {
            return servings;
        }

        public int getCalories() {
            return calories;
        }

        public int getFat() {
            return fat;
        }

        public int getSodium() {
            return sodium;
        }

        public int getCarbohydrate() {
            return carbohydrate;
        }
    }

    private interface Builder<T> {

        public T build();

    }

}
