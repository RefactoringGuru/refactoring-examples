package refactoring_guru.patterns.composite.example;

import javax.swing.*;
import java.awt.*;

public class Dot implements Graphic {
    public int x;
    public int y;
    public Color color;
    public JComponent component;

    public Dot(){}

    public Dot(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public int[] getSize() {
        int[] size = new int[2];
        size[0] = x + 10;
        size[1] = y + 30;
        return size;
    }

    @Override
    public void draw() {
        component = new JDot(this);
        JFrame frame = new JFrame();
        frame.setSize(100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(component);
        frame.setVisible(true);
    }

    @Override
    public JComponent getComponent() {
        return component = new JDot(this);
    }

    private class JDot extends JComponent {
        public Dot dot;

        public JDot(Dot dot) {
            this.dot = dot;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(dot.color);
            g.drawOval(dot.x, dot.y, 2, 2);
        }
    }
}
