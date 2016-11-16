package refactoring_guru.patterns.flyweight.example;

import javax.swing.*;
import java.awt.*;

public class TreeDrawer extends JFrame {
    Color color;

    public TreeDrawer(Color color) {
        this.color = color;
    }

    @Override
    public void paint(Graphics graphics) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        this.getContentPane().add(panel);
        graphics.setColor(Color.black);
        graphics.fillRect(150, 150, 10, 100);
        graphics.setColor(color);
        graphics.fillOval(135, 115, 120, 70);
        graphics.fillOval(65, 125, 120, 70);
        graphics.fillOval(90, 85, 120, 70);
    }
}
