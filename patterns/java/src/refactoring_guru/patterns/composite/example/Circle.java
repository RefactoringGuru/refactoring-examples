package refactoring_guru.patterns.composite.example;

import javax.swing.*;
import java.awt.*;

public class Circle extends Dot {
    public int radius;

    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
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
        size[0] = x + radius * 2;
        size[1] = y + radius * 2 + 30;
        return size;
    }

    @Override
    public void draw() {
        component = new JCircle(this);
        JFrame frame = new JFrame();
        frame.setSize(100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(component);
        frame.setVisible(true);
    }

    @Override
    public JComponent getComponent() {
        return component = new JCircle(this);
    }

    private class JCircle extends JComponent {
        public Circle circle;

        public JCircle(Circle circle) {
            this.circle = circle;
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(circle.color);
            g.drawOval(circle.x, circle.y, circle.radius * 2, circle.radius * 2);
        }
    }
}
