package refactoring_guru.patterns.composite.example.graphics;

import javax.swing.*;
import java.awt.*;

public class Dot extends Canvas implements Graphic {
    public int x;
    public int y;
    public Color color;
    private final int DOT_SIZE = 100;

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
    public void draw() {
<<<<<<< HEAD
        JFrame frame = new JFrame("Dot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(x + DOT_SIZE, y + DOT_SIZE);
        frame.add(this);
        frame.setVisible(true);
=======
        JFrame frame = getFrame(x + mn, y + mn, component);
>>>>>>> eaf0f8dc5bfdf95180459d674f648d2cac88c440
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillOval(x, y, 2, 2);
    }
}
