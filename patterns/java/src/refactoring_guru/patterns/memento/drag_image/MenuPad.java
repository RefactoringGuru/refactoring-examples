package refactoring_guru.patterns.memento.drag_image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class MenuPad extends MouseAdapter {
    private JPopupMenu menu = new JPopupMenu();
    private Editor editor;
    private Memento backup;

    public MenuPad(Editor editor, Memento backup) {
        this.editor = editor;
        this.backup = backup;
        initMenu();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public void initMenu() {
        JMenuItem circle = new JMenuItem("Add circle");
        circle.addActionListener(actionEvent -> {
            backup.setBackup(editor.items);
            editor.addItem(new Ellipse2D.Double(80, 40, 80, 80), Color.CYAN);
            editor.repaint();
        });
        menu.add(circle);

        JMenuItem rectangle = new JMenuItem("Add rectangle");
        rectangle.addActionListener(actionEvent -> {
            backup.setBackup(editor.items);
            editor.addItem(new Rectangle2D.Double(40, 60, 50,50), Color.MAGENTA);
            editor.repaint();
        });
        menu.add(rectangle);

        JMenuItem undo = new JMenuItem("Undo last added");
        undo.addActionListener(actionEvent -> {
            editor.items = backup.restore();
            editor.repaint();
        });
        menu.add(undo);
    }
}
