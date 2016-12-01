package refactoring_guru.patterns.composite.example;

import refactoring_guru.patterns.composite.example.graphics.Graphic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompoundGraphic implements Graphic {
    List<Graphic> children = new ArrayList<>();
    List<List<Graphic>> groups = new ArrayList<>();

    public void add(Graphic child) {
        children.add(child);
    }

    public void add(Graphic...components) {
        List<Graphic> list = new ArrayList<>(Arrays.asList(components));
        groups.add(list);
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
    public void draw() {
        JFrame frame = new JFrame("All graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(3, 3));
        frame.setSize(500, 500);
        frame.getContentPane().add(panel);
        if (children.size() > 0) {
            for (Graphic graphic : children) {
                panel.add((Canvas)graphic);
            }
        }
        if (groups.size() > 0) {
            for (List list : groups) {
                for (Object graphic : list) {
                    panel.add((Canvas)graphic);
                }
            }
        }
        frame.setVisible(true);
    }
}
