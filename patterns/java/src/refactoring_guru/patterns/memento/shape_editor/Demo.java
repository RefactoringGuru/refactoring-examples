package refactoring_guru.patterns.memento.shape_editor;

import refactoring_guru.patterns.memento.shape_editor.editor.Editor;
import refactoring_guru.patterns.memento.shape_editor.shapes.*;
import refactoring_guru.patterns.memento.shape_editor.shapes.Rectangle;

import java.awt.*;

// TODO: Add labels on the form.
// TODO: Add history list on the form.
// TODO: Comments.
public class Demo {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.loadShapes(
                new Circle(10, 10, 10, Color.BLUE),

                new CompoundShape(
                        new Circle(110, 110, 50, Color.RED),
                        new Dot(160, 160, Color.RED)
                ),

                new CompoundShape(
                        new Rectangle(250, 250, 100, 100, Color.GREEN),
                        new Dot(240, 240, Color.GREEN),
                        new Dot(240, 360, Color.GREEN),
                        new Dot(360, 360, Color.GREEN),
                        new Dot(360, 240, Color.GREEN)
                )
        );
    }
}
