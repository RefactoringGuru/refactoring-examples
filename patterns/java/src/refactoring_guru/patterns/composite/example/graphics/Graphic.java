package refactoring_guru.patterns.composite.example.graphics;

import javax.swing.*;
import java.awt.*;

public interface Graphic {
    public void move(int x, int y);
    public void draw();
    public int[] getSize();
    public JComponent getComponent();
    public void paint(Graphics graphics);
}
