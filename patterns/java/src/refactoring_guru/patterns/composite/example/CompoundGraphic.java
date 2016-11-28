package refactoring_guru.patterns.composite.example;

import refactoring_guru.patterns.composite.example.graphics.BasicGraphic;
import refactoring_guru.patterns.composite.example.graphics.Graphic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompoundGraphic extends BasicGraphic implements Graphic {
    List<Graphic> children = new ArrayList<>();

    public void add(Graphic child) {
        children.add(child);
    }

    public void add(Graphic...components) {
        for (Graphic graphic : components) {
            children.add(graphic);
        }
    }

    public void add(CompoundGraphic compoundGraphic) {
        this.children.addAll(compoundGraphic.children);
    }

    public void remove(Graphic child) {
        int i = children.indexOf(child);
        children.remove(i);
    }

    public void remove(Graphic...components) {
        List<Graphic> list = new ArrayList<>(Arrays.asList(components));
        for (Graphic g : list) {
            int i = children.indexOf(g);
            children.remove(i);
        }
    }

    @Override
    public void move(int x, int y) {
        for (Graphic child : children) {
            child.move(x, y);
        }
    }

    @Override
    public int[] getSize() {
        int width = 0;
        int height = 0;
        for (Graphic child : children) {
            int[] size = child.getSize();
            if (size[0] > width) {
                width += size[0] * 2;
            }
            if (size[1] > height) {
                height += size[1];
            }
        }
        return new int[] {width, height};
    }

    @Override
    public void draw() {
        int width = getSize()[0];
        int height = getSize()[1];
        JFrame frame = getFrame(width, height);
        frame.setLayout(new GridLayout());
        for (Graphic child : children) {
            JComponent component = child.getComponent();
            frame.getContentPane().add(component, new BorderLayout());
        }
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics graphic) {}

    @Override
    public JComponent getComponent() {
        return null;
    }
}
