package refactoring_guru.patterns.composite.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CompoundGraphic implements Graphic {
    List<Graphic> children = new ArrayList<>();

    public void add(Graphic child) {
        children.add(child);
    }

    public void add(List<Graphic> components) {
        this.children.addAll(components);
    }

    public void add(CompoundGraphic compoundGraphic) {
        this.children.addAll(compoundGraphic.children);
    }

    public void remove(Graphic child) {
        int i = children.indexOf(child);
        children.remove(i);
    }

    public void remove(List<Graphic> components) {
        this.children.removeAll(components);
    }

    @Override
    public void move(int x, int y) {
        for (Graphic child : children) {
            child.move(x, y);
        }
    }

    @Override
    public void draw() {
        int width = 0;
        int height = 0;
        for (Graphic child : children) {
            if (child.getClass() == Dot.class) {
                Dot dot = (Dot)child;
                if (width < dot.x) {
                    width = dot.x;
                }
                if (height < dot.y) {
                    height = dot.y;
                }
            }
            if (child.getClass() == Circle.class) {
                Circle circle = (Circle)child;
                if (width < (circle.x + circle.radius)) {
                    width += circle.x + circle.radius * 2;
                }
                if (height < (circle.y + circle.radius)) {
                    height += circle.y + circle.radius * 2;
                }
            }
        }
        width += 10;
        height += 10;
        ChildDrawer drawer = new ChildDrawer(children);
        drawer.setSize(width, height);
        drawer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawer.setVisible(true);
    }

    static class ChildDrawer extends JFrame {
        List<Graphic> children;

        public ChildDrawer(List children) {
            this.children = children;
        }

        @Override
        public void paint(Graphics g) {
            for (Graphic graphic : children) {
                if (graphic.getClass() == Dot.class) {
                    Dot dot = (Dot)graphic;
                    g.setColor(Color.RED);
                    g.drawOval(dot.x, dot.y, 1, 1);
                } else if (graphic.getClass() == Circle.class) {
                    Circle circle = (Circle)graphic;
                    g.setColor(Color.BLUE);
                    g.drawOval(circle.x, circle.y, circle.radius * 2, circle.radius * 2);
                }
            }
        }
    }
}
