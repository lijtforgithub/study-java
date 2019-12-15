package com.ljt.study.dp.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author LiJingTang
 * @date 2019-12-15 21:11
 */
public class Weather {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        new CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurements(80, 65, 30.4f);
    }

    private static class WeatherData extends Observable {

        private float temperature;
        private float humidity;
        private float pressure;

        public WeatherData() {
            super();
        }

        private void measurementsChanged() {
            this.setChanged();
            this.notifyObservers();
        }

        public void setMeasurements(float temperature, float humidity, float pressure) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.pressure = pressure;
            this.measurementsChanged();
        }

        public float getTemperature() {
            return temperature;
        }

        public float getHumidity() {
            return humidity;
        }

        public float getPressure() {
            return pressure;
        }
    }

    private static class CurrentConditionsDisplay implements Observer, DisplayElement {

        private Observable observable;
        private float temperature;
        private float humidity;

        public CurrentConditionsDisplay(Observable observable) {
            super();
            this.observable = observable;
            observable.addObserver(this);
        }

        @Override
        public void display() {
            System.out.println("Current conditions: " + this.temperature + "F degrees and " + this.humidity + "% humidity");
        }

        @Override
        public void update(Observable o, Object arg) {
            if (o instanceof WeatherData) {
                WeatherData weatherData = (WeatherData) o;
                this.temperature = weatherData.getTemperature();
                this.humidity = weatherData.getHumidity();
                this.display();
            }
        }
    }

    private interface DisplayElement {

        void display();

    }

}
