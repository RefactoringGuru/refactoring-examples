package refactoring_guru.patterns.visitor.example.shapes;

import refactoring_guru.patterns.visitor.example.Visitor;

public class Dot implements Shape {
    public int id;
    public int x;
    public int y;

    public Dot() {}

    public Dot(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(int x, int y) {
        // TODO
        // moving shape
    }

    @Override
    public void draw() {
        // TODO
        // droving shape
    }

    public String accept(Visitor visitor) {
        return visitor.visitDot(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }
}
