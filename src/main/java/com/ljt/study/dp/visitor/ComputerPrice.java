package com.ljt.study.dp.visitor;

import java.util.stream.Stream;

/**
 * 访问者模式
 *
 * @author LiJingTang
 * @date 2019-12-10 14:31
 */
public class ComputerPrice {

    public static void main(String[] args) {
        PersonalVisitor p = new PersonalVisitor();
        CorpVisitor c = new CorpVisitor();
        Computer computer = new Computer();
        computer.accept(p);
        computer.accept(c);
        System.out.println("电脑原价：" + computer.totalPrice);
        System.out.println("个人客户：" + p.totalPrice);
        System.out.println("企业客户：" + c.totalPrice);
    }


    private static class Computer {

        double totalPrice = 0.0;
        ComputerPart[] parts;

        public Computer() {
            parts = new ComputerPart[]{new CPU(), new Memory(), new Board()};
            totalPrice = Stream.of(parts).mapToDouble(ComputerPart::getPrice).sum();
        }

        public void accept(ComputerPartVisitor visitor) {
            Stream.of(parts).forEach(p -> p.accept(visitor));
        }
    }

    private static class CPU extends ComputerPart {

        @Override
        void accept(ComputerPartVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        double getPrice() {
            return 1000;
        }
    }

    private static class Memory extends ComputerPart {

        @Override
        void accept(ComputerPartVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        double getPrice() {
            return 800;
        }
    }

    private static class Board extends ComputerPart {

        @Override
        void accept(ComputerPartVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        double getPrice() {
            return 300;
        }
    }

    /**
     * 结构接口
     */
    private abstract static class ComputerPart {

        abstract void accept(ComputerPartVisitor visitor);

        abstract double getPrice();
    }

    /**
     * 访问接口
     */
    private interface ComputerPartVisitor {

        void visit(CPU cpu);

        void visit(Memory memory);

        void visit(Board board);
    }

    /**
     * 个人客户
     */
    private static class PersonalVisitor implements ComputerPartVisitor {

        double totalPrice = 0.0;

        @Override
        public void visit(CPU cpu) {
            totalPrice += cpu.getPrice() * 0.9;
        }

        @Override
        public void visit(Memory memory) {
            totalPrice += memory.getPrice() * 0.85;
        }

        @Override
        public void visit(Board board) {
            totalPrice += board.getPrice() * 0.95;
        }
    }

    /**
     * 企业客户
     */
    private static class CorpVisitor implements ComputerPartVisitor {

        double totalPrice = 0.0;

        @Override
        public void visit(CPU cpu) {
            totalPrice += cpu.getPrice() * 0.6;
        }

        @Override
        public void visit(Memory memory) {
            totalPrice += memory.getPrice() * 0.75;
        }

        @Override
        public void visit(Board board) {
            totalPrice += board.getPrice() * 0.75;
        }
    }

}
