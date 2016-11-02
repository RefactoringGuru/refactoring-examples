package refactoring_guru.patterns.prototype.example;

abstract class Shape {
    int x;
    int y;
    String color;

    public Shape() {}

    public Shape(Shape target) {
        if (target != null) {
            this.x = target.x;
            this.y = target.y;
            this.color = target.color;
        }
    }

    @Override
    public abstract Shape clone();
}
