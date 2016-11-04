package refactoring_guru.patterns.cache_prototype.example;

import refactoring_guru.patterns.prototype.example.Rectangle;

public class Application {
    PrototypeCache cache;

    public Application() {
        this.cache = new PrototypeCache();
        this.cache.loadCash();
    }

    public static void main(String[] args) {
        Application app = new Application();
        Rectangle rectangle = (Rectangle)app.cache.getByType("Rectangle");
        Rectangle anotherRectangle = (Rectangle)app.cache.getByType("Rectangle");
        System.out.println(rectangle.equals(anotherRectangle)); // true
    }
}
