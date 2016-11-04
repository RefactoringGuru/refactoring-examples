package refactoring_guru.patterns.cash_prototype.example;

import refactoring_guru.patterns.prototype.example.Rectangle;

public class Application {
    public static void main(String[] args) {
        PrototypeCash cash = new PrototypeCash();
        cash.loadCash();
        Rectangle rectangle = (Rectangle)cash.getByType("Rectangle");
        cash = null;
        System.gc();
        System.out.println("Square of rectangle = " + rectangle.width * rectangle.height);
    }
}
