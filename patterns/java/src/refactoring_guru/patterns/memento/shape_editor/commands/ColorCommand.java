package refactoring_guru.patterns.memento.shape_editor.commands;

import refactoring_guru.patterns.memento.shape_editor.editor.Editor;
import refactoring_guru.patterns.memento.shape_editor.shapes.Shape;

import java.awt.*;

public class ColorCommand extends RevertableCommand {
    private Color color;

    public ColorCommand(Editor editor, Color color) {
        super(editor);
        this.color = color;
    }

    @Override
    public String getName() {
        return "Colorize: " + color.toString();
    }

    @Override
    public void execute() {
        for (Shape child : editor.getShapes().getSelected()) {
            child.setColor(color);
        }
    }
}
