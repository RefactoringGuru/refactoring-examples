package refactoring_guru.patterns.visitor.example.shapes;

import refactoring_guru.patterns.visitor.example.Visitor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "compound_graphic")
public class CompoundGraphic implements Graphic {
    @XmlElement
    public int id;
    @XmlElement(name = "shape")
    public List<Shape> children = new ArrayList<>();

    public CompoundGraphic() {}

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
    public void accept(Visitor visitor) {
        visitor.visitCompoundGraphic(this);
    }

    public void add(Shape shape) {
        children.add(shape);
    }
}
