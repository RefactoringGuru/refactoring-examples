package refactoring_guru.patterns.memento.shape_editor.shapes;

import java.awt.*;
import java.io.Serializable;

public interface Shape extends Serializable {
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();
    public void drag();
    public void jump(int x, int y);
    public void move(int x, int y);
    public void drop();
    public Boolean isInsideBounds(int x, int y);
    public Color getColor();
    public void setColor(Color color);
    public void select();
    public void unSelect();
    public Boolean isSelected();
    public void paint(Graphics graphics);
}

