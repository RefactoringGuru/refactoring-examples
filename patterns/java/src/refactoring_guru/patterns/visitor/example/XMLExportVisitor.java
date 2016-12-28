package refactoring_guru.patterns.visitor.example;

import refactoring_guru.patterns.visitor.example.shapes.Circle;
import refactoring_guru.patterns.visitor.example.shapes.CompoundGraphic;
import refactoring_guru.patterns.visitor.example.shapes.Dot;
import refactoring_guru.patterns.visitor.example.shapes.Rectangle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XMLExportVisitor implements Visitor {
    JAXBContext context;
    Marshaller marshaller;

    public void visitDot(Dot d) {
        try {
            File dot = new File("D:/dot.xml");
            context = JAXBContext.newInstance(Dot.class);
            marshallerSettings();
            marshaller.marshal(d, dot);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    public void visitCircle(Circle c) {
        try {
            File circle = new File("D:/circle.xml");
            context = JAXBContext.newInstance(Circle.class);
            marshallerSettings();
            marshaller.marshal(c, circle);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    public void visitRectangle(Rectangle r) {
        try {
            File rectangle = new File("D:/rectangle.xml");
            context = JAXBContext.newInstance(Rectangle.class);
            marshallerSettings();
            marshaller.marshal(r, rectangle);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    public void visitCompoundGraphic(CompoundGraphic cg) {
        try {
            File compound = new File("D:/compound_graphic.xml");
            context = JAXBContext.newInstance(CompoundGraphic.class);
            marshallerSettings();
            marshaller.marshal(cg, compound);
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    public void marshallerSettings() throws JAXBException {
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    }
}
