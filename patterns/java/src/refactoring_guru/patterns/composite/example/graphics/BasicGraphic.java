package refactoring_guru.patterns.composite.example.graphics;

import javax.swing.*;
import java.awt.*;

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
        return frame;
    }

    public class JDot extends JComponent {
        public Dot dot;

        public JDot(Dot dot) {
            this.dot = dot;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(dot.color);
            g.drawOval(dot.x, dot.y, 2, 2);
        }
    }

    public class JCircle extends JComponent {
        public Circle circle;

        public JCircle(Circle circle) {
            this.circle = circle;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(circle.color);
            g.drawOval(circle.x, circle.y, circle.radius * 2, circle.radius * 2);
        }
    }
}
