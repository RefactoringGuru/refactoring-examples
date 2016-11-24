package refactoring_guru.patterns.composite.example;

import refactoring_guru.patterns.composite.example.graphics.Circle;
import refactoring_guru.patterns.composite.example.graphics.Dot;
import refactoring_guru.patterns.composite.example.graphics.Graphic;

import java.awt.*;

public class ImageEditor {
    public CompoundGraphic all = new CompoundGraphic();

    public void load() {
        all.add(new Dot(20, 120, Color.RED));
        all.add(new Circle(15, 30, 40, Color.BLUE));
        all.add(new Circle(15, 90, 20, Color.GREEN));
    }

    public void groupSelected(Graphic...components) {
        CompoundGraphic group = new CompoundGraphic();
        group.add(components);
        all.remove(components);
        all.add(group);
        all.draw();
    }
}
