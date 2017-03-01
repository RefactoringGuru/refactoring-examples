package refactoring_guru.patterns.memento.drag_image.shapes;

import java.awt.*;

public class Rectangle extends BaseShape {
    private int height;
    private int width;

    public Rectangle(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.fillRect(x, y, getWidth() - 1, getHeight() - 1);
    }
}
