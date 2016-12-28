package refactoring_guru.patterns.visitor.example.shapes;

import refactoring_guru.patterns.visitor.example.Visitor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "dot")
@XmlType(propOrder = {"id", "x", "y"})
public class Dot extends Shape {
    @XmlElement
    public int id;
    @XmlElement
    public int x;
    @XmlElement
    public int y;

    public Dot() {}

    public Dot(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void accept(Visitor visitor) {
        visitor.visitDot(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int getId() {
        return super.getId();
    }
}
