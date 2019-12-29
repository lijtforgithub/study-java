package com.ljt.study.pp.effective.chap02;

/**
 * @author LiJingTang
 * @date 2019-12-28 21:13
 */
public class Item02JavaBean {

    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts();
        cocaCola.setServingSize(240);
        cocaCola.setServings(8);
        cocaCola.setCalories(35);
        cocaCola.setCarbohydrate(27);

        System.out.println(cocaCola.hashCode());
    }

    /**
     * JavaBean模式(在构造过程中JavaBean可能处于不一致状态)
     */
    private static class NutritionFacts {

        private int servingSize = -1;
        private int servings = -1;
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public NutritionFacts() {
            super();
        }

        public void setServingSize(int servingSize) {
            this.servingSize = servingSize;
        }

        public void setServings(int servings) {
            this.servings = servings;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public void setFat(int fat) {
            this.fat = fat;
        }

        public void setSodium(int sodium) {
            this.sodium = sodium;
        }

        public void setCarbohydrate(int carbohydrate) {
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
