package refactoring_guru.patterns.composite.example;

import javax.swing.*;
import java.awt.*;

public class Circle extends Dot{
    public int radius;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw() {
        JCircle circle = new JCircle(this);
        circle.setSize(300, 300);
        circle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        circle.setVisible(true);
    }

    static class JCircle extends JFrame {
        public Circle circle;

        public JCircle(Circle circle) {
            this.circle = circle;
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.BLUE);
            g.drawOval(circle.x, circle.y, circle.radius * 2, circle.radius * 2);
        }
    }
}
