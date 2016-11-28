package refactoring_guru.patterns.composite.example.graphics;

import javax.swing.*;
import java.awt.*;

public class Dot extends BasicGraphic implements Graphic {
    public int x;
    public int y;
    public Color color;
    private final int mn = 30;

    public Dot(){}

    public Dot(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.component = BasicGraphic.createFigure(this);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public int[] getSize() {
        int[] size = new int[2];
        size[0] = x + mn;
        size[1] = y + mn;
        return size;
    }

    @Override
    public void draw() {
        JFrame frame = getFrame(x + mn, y + mn, component);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.drawOval(x, y, 2, 2);
    }
}
