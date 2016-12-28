package refactoring_guru.patterns.visitor.example.shapes;

import refactoring_guru.patterns.visitor.example.Visitor;

import javax.xml.bind.annotation.XmlElement;

public class Shape implements Graphic {
    @XmlElement
    public int id;

    public Shape() {}

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

    @Override
    public void accept(Visitor visitor) {

    }

    public int getId() {
        return id;
    }
}
