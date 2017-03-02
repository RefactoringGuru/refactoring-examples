package refactoring_guru.patterns.memento.drag_image;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class ShapesDragging extends JComponent {
    private Memento backup = new Memento();
    private double dx;
    private double dy;
    private double scale;
    public List<Item> items;

    public ShapesDragging() {
        items = new ArrayList<>();
        dx = 0.0;
        dy = 0.0;
        scale = 1.0;
        DragEngine dragEngine = new DragEngine();
        MenuPad menuPad = new MenuPad(this, backup);
        addMouseListener(dragEngine);
        addMouseListener(menuPad);
        addMouseMotionListener(dragEngine);
        addMouseMotionListener(menuPad);
        addMouseWheelListener(menuPad);
    }
 
    public void addItem(Shape shape, Color color) {
        AffineTransform transform = AffineTransform.getTranslateInstance(0, 0);
        addItem(shape, color, transform);
    }
 
    public void addItem(Shape shape, Color color, AffineTransform transform) {
        Item item = new Item();
        item.shape = shape;
        item.color = color;
        item.transform = transform;
        items.add(item);
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(dx, dy);
        g2d.scale(scale, scale);
        for (Item item : items) {
            AffineTransform saved = g2d.getTransform();
            g2d.transform(item.transform);
            g2d.setColor(item.color);
            g2d.fill(item.shape);
            g2d.setTransform(saved);
        }
    }
 
    private Point2D mapToScene(Point point) {
        double x = (point.x - dx) / scale;
        double y = (point.y - dy) / scale;
        return new Point2D.Double(x, y);
    }
 
    private Item findItem(Point2D point) {
        // process items in the order reversed to drawing order
        ListIterator<Item> iterator = items.listIterator(items.size());
        while (iterator.hasPrevious()) {
            Item item = iterator.previous();
            AffineTransform transform = item.transform;
            Shape shape = item.shape;
            try {
                if (shape.contains(transform.inverseTransform(point, null)))
                    return item;
            } catch (NoninvertibleTransformException ignored) {
            }
        }
        return null;
    }

    private class DragEngine extends MouseAdapter {
        private Item hoverItem;
        private boolean isDragging;
        private Point previous;
        private Cursor savedCursor;

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!isDragging)
                return;
            // don't move item if both mouse buttons are pressed
            if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) == 0) {
                Point2D point = mapToScene(e.getPoint());
                Point2D prevPoint = mapToScene(previous);
                double tx = point.getX() - prevPoint.getX();
                double ty = point.getY() - prevPoint.getY();
                AffineTransform transform = AffineTransform.getTranslateInstance(tx, ty);
                transform.concatenate(hoverItem.transform);
                hoverItem.transform = transform;
                repaint();
            }
            previous = new Point(e.getPoint());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point2D point = mapToScene(e.getPoint());
            Item item = findItem(point);
            if (hoverItem == null && item != null) {
                savedCursor = getCursor();
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else if (hoverItem != null && item == null) {
                setCursor(savedCursor);
            }
            hoverItem = item;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && !isDragging && hoverItem != null) {
                isDragging = true;
                previous = new Point(e.getPoint());
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && isDragging) {
                isDragging = false;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Shapes Dragging");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ShapesDragging shapesDragging = new ShapesDragging();
            shapesDragging.addItem(new Rectangle2D.Double(30, 30, 150, 100), Color.RED);
            shapesDragging.addItem(new Rectangle2D.Double(20, 120, 40, 40), Color.BLUE);
            frame.getContentPane().add(shapesDragging);
            frame.setSize(600, 400);
            frame.setVisible(true);
        });
    }
}
