package refactoring_guru.patterns.flyweight.example;

import java.awt.*;

public class TreeType {
    private Color color;
    private String name;

    public TreeType(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void draw(int x, int y) {
        TreeDrawer drawer = new TreeDrawer(this.color);
        drawer.setSize(x, y);
        drawer.setVisible(true);
    }
}
