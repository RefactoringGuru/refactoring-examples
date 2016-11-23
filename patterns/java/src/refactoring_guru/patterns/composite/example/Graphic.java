package refactoring_guru.patterns.composite.example;

import javax.swing.*;

public interface Graphic {
    public void move(int x, int y);
    public void draw();
    public int[] getSize();
    public JComponent getComponent();
}
