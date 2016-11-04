package refactoring_guru.patterns.cash_prototype.example;

import refactoring_guru.patterns.prototype.example.Circle;
import refactoring_guru.patterns.prototype.example.Rectangle;
import refactoring_guru.patterns.prototype.example.Shape;

import java.util.ArrayList;
import java.util.List;

public class PrototypeCash {
    List<Shape> cash = new ArrayList<>();

    public void loadCash() {
        Circle circle = new Circle();
        circle.x = 5;
        circle.y = 7;
        circle.radius = 10;

        Rectangle rectangle = new Rectangle();
        rectangle.x = 6;
        rectangle.y = 9;
        rectangle.width = 8;
        rectangle.height = 10;

        cash.add(circle);
        cash.add(rectangle);
    }

    public Shape getById(int id) {
        return cash.get(id).clone();
    }

    public Shape getByType(String type) {
        for (Shape shape : cash) {
            if (shape.getClass().getSimpleName().equals(type)) {
                return shape.clone();
            }
        }
        return null;
    }
}
