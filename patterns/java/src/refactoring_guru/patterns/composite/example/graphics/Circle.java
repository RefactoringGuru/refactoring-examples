package refactoring_guru.patterns.composite.example.graphics;

import java.awt.*;

public class Circle extends Dot {
    public int radius;
    private final int mn = 30;

    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
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
        size[0] = x + radius * 2 + mn;
        size[1] = y + radius * 2 + mn;
        return size;
    }

    @Override
    public void draw() {
        getFrame(getSize()[0], getSize()[1], component);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.drawOval(x, y, radius * 2, radius * 2);
    }
}
