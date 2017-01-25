package refactoring_guru.patterns.visitor.example.visitor;

import refactoring_guru.patterns.visitor.example.shapes.*;

public interface Visitor {
    public String visitDot(Dot dot);

    public String visitCircle(Circle circle);

    public String visitRectangle(Rectangle rectangle);

    public String visitCompoundGraphic(CompoundShape cg);
}
