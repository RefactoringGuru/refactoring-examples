package refactoring_guru.patterns.composite.example.graphics;

import javax.swing.*;
import java.awt.*;

public class Dot extends BasicGraphic implements Graphic {
    public int x;
    public int y;
    public Color color;

    public Dot(){}

    public Dot(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.component = new JDot(this);
    }

    @Override
    public void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public int[] getSize() {
        int[] size = new int[2];
        size[0] = x + 10;
        size[1] = y + 30;
        return size;
    }

    @Override
    public void draw() {
        JFrame frame = getFrame(x + 30, y + 30, component);
        frame.setVisible(true);
    }
}
