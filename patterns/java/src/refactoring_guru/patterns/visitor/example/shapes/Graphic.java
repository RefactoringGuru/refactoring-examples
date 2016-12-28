package refactoring_guru.patterns.visitor.example.shapes;

import refactoring_guru.patterns.visitor.example.Visitor;

public interface Graphic {
    public void move(int x, int y);
    public void draw();
    public void accept(Visitor visitor);
}
