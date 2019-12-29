package com.ljt.study.pp.effective.chap02;

/**
 * @author LiJingTang
 * @date 2019-12-28 21:11
 */
public class Item02Constructor {

    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);

        System.out.println(cocaCola.hashCode());
    }

    /**
     * 重叠构造器模式
     */
    private static class NutritionFacts {

        private final int servingSize;
        private final int servings;
        private final int calories;
        private final int fat;
        private final int sodium;
        private final int carbohydrate;

        public NutritionFacts(int servingSize, int servings) {
            this(servingSize, servings, 0);
        }

        public NutritionFacts(int servingSize, int servings, int calories) {
            this(servingSize, servings, calories, 0);
        }

        public NutritionFacts(int servingSize, int servings, int calories, int fat) {
            this(servingSize, servings, calories, fat, 0);
        }

        public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
            this(servingSize, servings, calories, fat, sodium, 0);
        }

        public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
            super();
            this.servingSize = servingSize;
            this.servings = servings;
            this.calories = calories;
            this.fat = fat;
            this.sodium = sodium;
            this.carbohydrate = carbohydrate;
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

}
