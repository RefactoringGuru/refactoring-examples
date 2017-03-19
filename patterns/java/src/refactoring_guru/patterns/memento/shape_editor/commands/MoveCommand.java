package refactoring_guru.patterns.memento.shape_editor.commands;

import refactoring_guru.patterns.memento.shape_editor.editor.Editor;
import refactoring_guru.patterns.memento.shape_editor.shapes.Shape;

public class MoveCommand extends RevertableCommand {
    private int startX, startY;
    private int endX, endY;

    public MoveCommand(Editor editor) {
        super(editor);
    }

    @Override
    public String getName() {
        return "Move by X:" + (endX - startX) + " Y:" + (endY - startY);
    }

    public void start(int x, int y) {
        startX = x;
        startY = y;
        for (Shape child : editor.getShapes().getSelected()) {
            child.drag();
        }
    }

    public void move(int x, int y) {
        for (Shape child : editor.getShapes().getSelected()) {
            child.jump(x - startX, y - startY);
        }
    }

    public void finish(int x, int y) {
        endX = x;
        endY = y;
        for (Shape child : editor.getShapes().getSelected()) {
            child.drop();
        }
    }

    @Override
    public void execute() {
        for (Shape child : editor.getShapes().getSelected()) {
            child.move(endX - startX, endY - startY);
        }
    }
}
