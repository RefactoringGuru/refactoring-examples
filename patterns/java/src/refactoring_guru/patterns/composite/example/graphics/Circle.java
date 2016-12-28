package refactoring_guru.patterns.composite.example.graphics;

import javax.swing.*;
import java.awt.*;

public class Circle extends Dot {
    public int radius;
    private final int CIRCLE_SIZE = 100;

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
    public void draw() {
        JFrame frame = new JFrame("Circle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(x + radius * 2 + CIRCLE_SIZE, y + radius * 2 + CIRCLE_SIZE);
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.drawOval(x, y, radius * 2, radius * 2);
    }
}
