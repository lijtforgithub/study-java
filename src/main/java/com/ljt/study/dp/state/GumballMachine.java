package com.ljt.study.dp.state;

import java.util.Random;

/**
 * @author LiJingTang
 * @date 2019-12-14 16:48
 */
public class GumballMachine {

    public static void main(String[] args) {
        GumballMachine gumballMachine = new GumballMachine(5);

        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        System.out.println(gumballMachine);
    }

    private State soldOutState;
    private State noQuarterState;
    private State hasQuarterState;
    private State soldState;
    private State winnerSate;

    private State state = soldOutState;
    int count = 0;

    public GumballMachine(int numberGumballs) {
        super();
        this.soldOutState = new SoldOutState(this);
        this.noQuarterState = new NoQuarterState(this);
        this.hasQuarterState = new HasQuarterState(this);
        this.soldState = new SoldState(this);
        this.winnerSate = new WinnerState(this);
        this.count = numberGumballs;

        if (numberGumballs > 0) {
            this.state = this.noQuarterState;
        }
    }

    public void insertQuarter() {
        this.state.insertQuarter();
    }

    public void ejectQuarter() {
        this.state.ejectQuarter();
    }

    public void turnCrank() {
        this.state.turnCrank();
        this.state.dispense();
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void releaseBall() {
        System.out.println("A gumball comes rolling out the slot...");

        if (this.count != 0) {
            this.count--;
        }
    }

    public void refill(int count) {
        this.count = count;
        this.state = noQuarterState;
    }

    public State getSoldOutState() {
        return soldOutState;
    }

    public State getNoQuarterState() {
        return noQuarterState;
    }

    public State getHasQuarterState() {
        return hasQuarterState;
    }

    public State getSoldState() {
        return soldState;
    }

    public State getWinnerSate() {
        return winnerSate;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "GumballMachine [state=" + state.getClass().getSimpleName() + ", count=" + count + "]";
    }


    private interface State {

        /**
         * 投币
         */
        void insertQuarter();

        /**
         * 退币
         */
        void ejectQuarter();

        /**
         * 转动曲柄
         */
        void turnCrank();

        /**
         * 发放糖果
         */
        void dispense();

    }

    private static class HasQuarterState implements State {

        private Random randomWinner = new Random(System.currentTimeMillis());

        private GumballMachine gumballMachine;

        public HasQuarterState(GumballMachine gumballMachine) {
            super();
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertQuarter() {
            System.out.println("You can't insert another quarter");
        }

        @Override
        public void ejectQuarter() {
            System.out.println("Quarter returned");
            this.gumballMachine.setState(this.gumballMachine.getNoQuarterState());
        }

        @Override
        public void turnCrank() {
            System.out.println("You turned...");

            int winner = this.randomWinner.nextInt(10);

            if ((winner == 0) && (this.gumballMachine.getCount() > 0)) {
                this.gumballMachine.setState(this.gumballMachine.getWinnerSate());
            } else {
                this.gumballMachine.setState(this.gumballMachine.getSoldState());
            }
        }

        @Override
        public void dispense() {
            System.out.println("No gumball dispensed");
        }
    }

    private static class NoQuarterState implements State {

        private GumballMachine gumballMachine;

        public NoQuarterState(GumballMachine gumballMachine) {
            super();
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertQuarter() {
            System.out.println("You inserted a quarter");
            this.gumballMachine.setState(this.gumballMachine.getHasQuarterState());
        }

        @Override
        public void ejectQuarter() {
            System.out.println("You haven't inserted a quarter");
        }

        @Override
        public void turnCrank() {
            System.out.println("You turned, but there's no quarter");
        }

        @Override
        public void dispense() {
            System.out.println("You need to pay first");
        }
    }

    private static class SoldOutState implements State {

        private GumballMachine gumballMachine;

        public SoldOutState(GumballMachine gumballMachine) {
            super();
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertQuarter() {
            System.out.println("You can't insert a quarter, the machine is sold out");
        }

        @Override
        public void ejectQuarter() {
            System.out.println("You can't eject, you haven't inserted a quarter yet");
        }

        @Override
        public void turnCrank() {
            System.out.println("You turned, but there are no gumballs");
        }

        @Override
        public void dispense() {
            System.out.println("No gumballs dispense");
        }
    }

    private static class SoldState implements State {

        private GumballMachine gumballMachine;

        public SoldState(GumballMachine gumballMachine) {
            super();
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertQuarter() {
            System.out.println("Please wait, we're already giving you a gumball");
        }

        @Override
        public void ejectQuarter() {
            System.out.println("Sorry, you already turned the crank");
        }

        @Override
        public void turnCrank() {
            System.out.println("Turning twice doesn't get you another gumball!");
        }

        @Override
        public void dispense() {
            this.gumballMachine.releaseBall();

            if (this.gumballMachine.getCount() > 0) {
                this.gumballMachine.setState(this.gumballMachine.getNoQuarterState());
            } else {
                System.out.println("Oops, out of gumballs!");
                this.gumballMachine.setState(this.gumballMachine.getSoldOutState());
            }
        }
    }

    private static class WinnerState implements State {

        private GumballMachine gumballMachine;

        public WinnerState(GumballMachine gumballMachine) {
            super();
            this.gumballMachine = gumballMachine;
        }

        @Override
        public void insertQuarter() {
            System.out.println("Please wait, we're already giving you a gumball");
        }

        @Override
        public void ejectQuarter() {
            System.out.println("Sorry, you already turned the crank");
        }

        @Override
        public void turnCrank() {
            System.out.println("Turning twice doesn't get you another gumball!");
        }

        @Override
        public void dispense() {
            System.out.println("YOU'RE A WINNER! You get tow gumballs for your quarter");
            this.gumballMachine.releaseBall();

            if (this.gumballMachine.getCount() == 0) {
                this.gumballMachine.setState(this.gumballMachine.getNoQuarterState());
            } else {
                this.gumballMachine.releaseBall();

                if (this.gumballMachine.getCount() > 0) {
                    this.gumballMachine.setState(this.gumballMachine.getHasQuarterState());
                } else {
                    System.out.println("Oops, out of gumballs!");
                    this.gumballMachine.setState(this.gumballMachine.getSoldOutState());
                }
            }
        }
    }

}
