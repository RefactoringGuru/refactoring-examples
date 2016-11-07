package refactoring_guru.patterns.prototype.example.caching;

import refactoring_guru.patterns.prototype.example.Circle;
import refactoring_guru.patterns.prototype.example.Rectangle;
import refactoring_guru.patterns.prototype.example.Shape;

import java.util.HashMap;
import java.util.Map;

public class PrototypeCache {
    Map<String, Shape> cache = new HashMap<>();

    public void loadCash() {
        Circle circle = new Circle();
        circle.x = 5;
        circle.y = 7;
        circle.radius = 45;
        circle.color = "Green";

        Rectangle rectangle = new Rectangle();
        rectangle.x = 6;
        rectangle.y = 9;
        rectangle.width = 8;
        rectangle.height = 10;
        rectangle.color = "Blue";

        cache.put("Big green circle", circle);
        cache.put("Medium blue rectangle", rectangle);
    }

    public Shape getByType(String type) {
        return cache.get(type).clone();
    }
}
