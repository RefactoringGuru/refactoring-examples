package refactoring_guru.patterns.composite.example;

import java.awt.*;
import java.util.List;

public class ImageEditor {
    public CompoundGraphic all = new CompoundGraphic();

    public void load() {
        all.add(new Dot(10, 50, Color.RED));
        all.add(new Circle(25, 30, 10, Color.BLUE));
        all.add(new Circle(20, 30, 20, Color.GREEN));
    }

    public void groupSelected(List<Graphic> components) {
        CompoundGraphic group = new CompoundGraphic();
        group.add(components);
        all.remove(components);
        all.add(group);
        all.draw();
    }
}
