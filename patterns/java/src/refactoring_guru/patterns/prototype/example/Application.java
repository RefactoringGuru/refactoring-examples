package refactoring_guru.patterns.prototype.example;

import java.util.ArrayList;
import java.util.List;

public class Application {
    List<Shape> shapes = new ArrayList<>();
    List<Shape> shapesCopy = new ArrayList<>();

    public Application() {
        Circle circle = new Circle();
        circle.x = 10;
        circle.y = 20;
        circle.radius = 15;
        shapes.add(circle);

        Circle anotherCircle = (Circle)circle.clone();
        shapes.add(anotherCircle);

        Rectangle rectangle = new Rectangle();
        rectangle.width = 10;
        rectangle.height = 20;
        shapes.add(rectangle);
    }

    public void businessLogic() {
        for (Shape shape : shapes) {
            shapesCopy.add(shape.clone());
        }
    }
}
