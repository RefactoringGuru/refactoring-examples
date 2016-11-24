package refactoring_guru.patterns.composite.example;

import refactoring_guru.patterns.composite.example.graphics.Graphic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompoundGraphic implements Graphic {
    List<Graphic> children = new ArrayList<>();

    public void add(Graphic child) {
        children.add(child);
    }

    public void add(Graphic...components) {
        Graphic[] array = components;
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
        return null;
    }

    @Override
    public JComponent getComponent() {
        return null;
    }

    @Override
    public void draw() {
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
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(new GridLayout());
        for (Graphic child : children) {
            JComponent component = child.getComponent();
            frame.getContentPane().add(component, new BorderLayout());
        }
        frame.setVisible(true);
    }
}
