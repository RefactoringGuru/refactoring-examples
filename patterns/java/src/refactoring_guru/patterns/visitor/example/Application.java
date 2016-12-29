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

        export(circle, compoundGraphic);
    }

    public static void export(Shape...args) {
        XMLExportVisitor exportVisitor = new XMLExportVisitor();
        StringBuilder sb = new StringBuilder();
        for (Shape shape : args) {
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
            sb.append(shape.accept(exportVisitor));
            System.out.println(sb.toString());
            sb.setLength(0);
        }
    }
}
