package refactoring_guru.patterns.visitor.example;

import refactoring_guru.patterns.visitor.example.shapes.*;

public class XMLExportVisitor implements Visitor {
    public String visitDot(Dot d) {
        return  "<dot>" + "\n" +
                "    <id>" + d.getId() + "</id>" + "\n" +
                "    <x>" + d.getX() + "</x>" + "\n" +
                "    <y>" + d.getY() + "</y>" + "\n" +
                "</dot>" + "\n";
    }

    public String visitCircle(Circle c) {
        return  "<circle>" + "\n" +
                "    <id>" + c.getId() + "</id>" + "\n" +
                "    <x>" + c.getX() + "</x>" + "\n" +
                "    <y>" + c.getY() + "</y>" + "\n" +
                "    <radius>" + c.getRadius() + "</radius>" + "\n" +
                "</circle>" + "\n";
    }

    public String visitRectangle(Rectangle r) {
        return  "<rectangle>" + "\n" +
                "    <id>" + r.getId() + "</id>" + "\n" +
                "    <x>" + r.getX() + "</x>" + "\n" +
                "    <y>" + r.getY() + "</y>" + "\n" +
                "    <width>" + r.getWidth() + "</width>" + "\n" +
                "    <height>" + r.getHeight() + "</height>" + "\n" +
                "</rectangle>" + "\n";
    }

    public String visitCompoundGraphic(CompoundGraphic cg) {
        StringBuilder sb = new StringBuilder();
        for (Shape shape : cg.children) {
            sb.append(shape.accept(this));
        }
        return sb.toString();
    }
}
