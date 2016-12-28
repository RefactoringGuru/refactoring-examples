package refactoring_guru.patterns.visitor.example;

import refactoring_guru.patterns.visitor.example.shapes.*;

public class Application {
    public static void main(String[] args) {
        Dot dot = new Dot(1, 10, 55);
        Circle circle = new Circle(2, 23, 15, 10);
        Rectangle rectangle = new Rectangle(3, 10, 17, 20, 30);

        CompoundGraphic compoundGraphic = new CompoundGraphic(4);
        compoundGraphic.add(dot);
        compoundGraphic.add(circle);
        compoundGraphic.add(rectangle);

        export(dot, circle, rectangle, compoundGraphic);
    }

    public static void export(Graphic...args) {
        XMLExportVisitor exportVisitor = new XMLExportVisitor();
        for (Graphic graphic : args) {
            graphic.accept(exportVisitor);
        }
    }
}
