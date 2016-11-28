package refactoring_guru.patterns.composite.example.graphics;

import javax.swing.*;
import java.awt.*;

public class JGraphic extends JComponent {
    Graphic graphic;

    public JGraphic(Graphic graphic) {
        this.graphic = graphic;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphic.paint(g);
    }
}
