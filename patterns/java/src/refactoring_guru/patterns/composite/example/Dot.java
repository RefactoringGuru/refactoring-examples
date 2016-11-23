package refactoring_guru.patterns.composite.example;

import javax.swing.*;
import java.awt.*;

public class Dot implements Graphic {
    public int x;
    public int y;

    public Dot(){}

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void draw() {
        JDot dot = new JDot(this);
        dot.setSize(300, 300);
        dot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dot.setVisible(true);
    }

    static class JDot extends JFrame {
        public Dot dot;

        public JDot(Dot dot) {
            this.dot = dot;
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.RED);
            g.drawOval(dot.x, dot.y, 5, 5);
        }
    }
}
