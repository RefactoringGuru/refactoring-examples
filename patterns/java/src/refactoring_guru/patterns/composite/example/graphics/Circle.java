package refactoring_guru.patterns.composite.example.graphics;

import javax.swing.*;
import java.awt.*;

public class Circle extends Dot {
    public int radius;

    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
        this.component = new JCircle(this);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public int[] getSize() {
        int[] size = new int[2];
        size[0] = x + radius * 2 + 30;
        size[1] = y + radius * 2 + 30;
        return size;
    }

    @Override
    public void draw() {
        JFrame frame = getFrame(x + radius * 2 + 30, y + radius * 2 + 30, component);
        frame.setVisible(true);
    }
}
