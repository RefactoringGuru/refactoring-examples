package refactoring_guru.patterns.memento.drug_image.shapes;

import java.awt.*;

public class Circle extends BaseShape {
    public int radius;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public int getWidth() {
        return radius * 2;
    }

    @Override
    public int getHeight() {
        return radius * 2;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillOval(x, y, getWidth() - 1, getHeight() - 1);
    }
}
