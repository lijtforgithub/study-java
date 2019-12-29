package com.ljt.study.pp.effective.chap04;

/**
 * @author LiJingTang
 * @date 2019-12-29 10:56
 */
public class Item15 {

    /**
     * 不可变类
     */
    private static final class Complex {

        private final double re;
        private final double im;

        public Complex(double re, double im) {
            super();
            this.re = re;
            this.im = im;
        }

        public double realPart() {
            return re;
        }

        public double imaginaryPart() {
            return im;
        }

        public Complex add(Complex c) {
            return new Complex(re + c.re, im + c.im);
        }

        public Complex subtract(Complex c) {
            return new Complex(re - c.re, im - c.im);
        }

        public Complex multiply(Complex c) {
            return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
        }

        public Complex divide(Complex c) {
            double tmp = c.re * c.re + c.im * c.im;

            return new Complex((re * c.re + im * c.im) / tmp, (im * c.re - re * c.im) / tmp);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Complex)) {
                return false;
            }
            Complex c = (Complex) obj;

            return Double.compare(re, c.re) == 0 && Double.compare(im, c.im) == 0;
        }

        @Override
        public int hashCode() {
            int result = 17 + hashDouble(re);
            result = 31 * result + hashDouble(im);

            return result;
        }

        private int hashDouble(double val) {
            long longBits = Double.doubleToLongBits(val);

            return (int) (longBits ^ (longBits >>> 32));
        }

        @Override
        public String toString() {
            return "(" + re + " + " + im + "i)";
        }
    }

    private static class NotFinalComplex {

        private final double re;
        private final double im;

        private NotFinalComplex(double re, double im) {
            super();
            this.re = re;
            this.im = im;
        }

        public static NotFinalComplex valueOf(double re, double im) {
            return new NotFinalComplex(re, im);
        }

        public double realPart() {
            return re;
        }

        public double imaginaryPart() {
            return im;
        }

        public Complex add(Complex c) {
            return new Complex(re + c.re, im + c.im);
        }

        public Complex subtract(Complex c) {
            return new Complex(re - c.re, im - c.im);
        }

        public Complex multiply(Complex c) {
            return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
        }

        public Complex divide(Complex c) {
            double tmp = c.re * c.re + c.im * c.im;

            return new Complex((re * c.re + im * c.im) / tmp, (im * c.re - re * c.im) / tmp);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Complex)) {
                return false;
            }
            Complex c = (Complex) obj;

            return Double.compare(re, c.re) == 0 && Double.compare(im, c.im) == 0;
        }

        @Override
        public int hashCode() {
            int result = 17 + hashDouble(re);
            result = 31 * result + hashDouble(im);

            return result;
        }

        private int hashDouble(double val) {
            long longBits = Double.doubleToLongBits(val);

            return (int) (longBits ^ (longBits >>> 32));
        }

        @Override
        public String toString() {
            return "(" + re + " + " + im + "i)";
        }
    }

}
