package refactoring_guru.patterns.visitor.example.shapes;

import refactoring_guru.patterns.visitor.example.Visitor;

import java.util.ArrayList;
import java.util.List;

public class CompoundGraphic implements Shape {
    public int id;
    public List<Shape> children = new ArrayList<>();

    public CompoundGraphic(int id) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    @Override
    public String accept(Visitor visitor) {
        return "<compound_graphic>" + "\n" +
                "<id>" + getId() + "</id>" + "\n" +
                visitor.visitCompoundGraphic(this) +
                "</compound_graphic>";
    }

    public void add(Shape shape) {
        children.add(shape);
    }
}
