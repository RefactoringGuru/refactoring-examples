package refactoring_guru.patterns.visitor.example;

import refactoring_guru.patterns.visitor.example.shapes.Circle;
import refactoring_guru.patterns.visitor.example.shapes.CompoundGraphic;
import refactoring_guru.patterns.visitor.example.shapes.Dot;
import refactoring_guru.patterns.visitor.example.shapes.Rectangle;

public interface Visitor {
    public void visitDot(Dot dot);
    public void visitCircle(Circle circle);
    public void visitRectangle(Rectangle rectangle);
    public void visitCompoundGraphic(CompoundGraphic cg);
}
