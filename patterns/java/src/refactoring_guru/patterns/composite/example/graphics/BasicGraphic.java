package refactoring_guru.patterns.composite.example.graphics;

import javax.swing.*;

public abstract class BasicGraphic {
    public JComponent component;

    public JComponent getComponent() {
        return component;
    }

    public JFrame getFrame(int x, int y, JComponent component) {
        JFrame frame = new JFrame();
        frame.setSize(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(component);
        frame.setVisible(true);
        return frame;
    }

    public JFrame getFrame(int x, int y) {
        JFrame frame = new JFrame();
        frame.setSize(x, y);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    public static JGraphic createFigure(Graphic graphic) {
        return new JGraphic(graphic);
    }
}
