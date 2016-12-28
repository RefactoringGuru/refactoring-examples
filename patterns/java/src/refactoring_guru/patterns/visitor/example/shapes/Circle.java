package refactoring_guru.patterns.visitor.example.shapes;

import refactoring_guru.patterns.visitor.example.Visitor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "circle")
@XmlType(propOrder = {"id", "x", "y", "radius"})
public class Circle extends Dot {
    @XmlElement
    public int id;
    @XmlElement
    public int x;
    @XmlElement
    public int y;
    @XmlElement
    public int radius;

    public Circle() {}

    public Circle(int id, int x, int y, int radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitCircle(this);
    }

    public int getRadius() {
        return radius;
    }
}
