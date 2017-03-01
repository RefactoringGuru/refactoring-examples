package refactoring_guru.patterns.memento.drug_image.editor;

import refactoring_guru.patterns.memento.drug_image.shapes.*;
import refactoring_guru.patterns.memento.drug_image.shapes.Shape;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Editor {
    private EditorCanvas canvas;
    private JPopupMenu menu;
    private Memento backup = new Memento();
    private List<Shape> shapes = new ArrayList<>();

    public Editor() {
        canvas = new EditorCanvas();
        menu = new JPopupMenu();
        initMenu();
    }

    private class EditorCanvas extends Canvas {
        JFrame frame;
        private static final int PADDING = 10;

        EditorCanvas() {
            createFrame();
            refresh();

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent mouseEvent) {
                    int x;
                    int y;
                    x = mouseEvent.getX();
                    y = mouseEvent.getY();
                    for (Shape shape : shapes) {
                        if (shape.isInsideBounds(x, y)) {
                            shape.move(x, y);
                        }
                    }
                    repaint();
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                        menu.show(mouseEvent.getComponent(), mouseEvent.getX(), mouseEvent.getY());
                    }
                }
            });
        }

        public void paint(Graphics graphics) {
            if (shapes.size() > 0) {
                for (Shape shape : shapes) {
                    shape.paint(graphics);
                }
            } else {
                canvas.refresh();
            }
        }

        void createFrame() {
            frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel contentPanel = new JPanel();
            Border padding = BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING);
            contentPanel.setBorder(padding);
            frame.setContentPane(contentPanel);

            frame.add(this);
            frame.setVisible(true);
            frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        }

        void refresh() {
            this.setSize(400, 400);
            frame.pack();
        }
    }



    public void initMenu() {
        JMenuItem circle = new JMenuItem("Add circle");
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                backup.setBackup(shapes);
                shapes.add(new Circle(20, 20, 20));
                canvas.repaint();
            }
        });
        menu.add(circle);

        JMenuItem rectangle = new JMenuItem("Add rectangle");
        rectangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                backup.setBackup(shapes);
                shapes.add(new refactoring_guru.patterns.memento.drug_image.shapes.Rectangle(20, 20, 50, 60));
                canvas.repaint();
            }
        });
        menu.add(rectangle);

        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                shapes = backup.restore();
                canvas.repaint();
            }
        });
        menu.add(undo);
    }
}
