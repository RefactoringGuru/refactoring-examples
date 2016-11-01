package refactoring_guru.patterns.prototype.example;

public class Application {
    public static void main(String[] args) {
        Circle circle = new Circle();
        circle.x = 10;
        circle.y = 20;
        circle.radius = 15;

        Circle newShape = (Circle)circle.clone();
        System.out.println(newShape.x); // 10
        System.out.println(newShape.y); // 20
        System.out.println(newShape.radius); // 15
    }
}
