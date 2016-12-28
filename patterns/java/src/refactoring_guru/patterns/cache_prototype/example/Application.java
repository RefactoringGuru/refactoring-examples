package refactoring_guru.patterns.cache_prototype.example;

import refactoring_guru.patterns.prototype.example.Rectangle;
import refactoring_guru.patterns.prototype.example.Shape;

public class Application {
    PrototypeCache cache;

    public Application() {
        this.cache = new PrototypeCache();
        this.cache.loadCash();
    }

    public static void main(String[] args) {
        Application app = new Application();
        Shape shape = app.cache.getByType("Big green circle");
        Shape anotherShape = app.cache.getByType("Medium blue rectangle");
    }
}