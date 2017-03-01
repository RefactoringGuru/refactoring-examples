package refactoring_guru.patterns.memento.drag_image.shapes;

import java.awt.*;

public interface Shape {
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    void move(int x, int y);
    Boolean isInsideBounds(int x, int y);
    void paint(Graphics graphics);
}
