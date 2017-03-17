package refactoring_guru.patterns.memento.shape_editor.commands;

import refactoring_guru.patterns.memento.shape_editor.editor.Memento;
import refactoring_guru.patterns.memento.shape_editor.editor.Editor;

abstract public class RevertableCommand implements Command {
    public Editor editor;
    protected Memento memento;

    public RevertableCommand(Editor editor) {
        this.editor = editor;
        memento = new Memento(this.editor);
    }

    @Override
    public void undo() {
        memento.restore();
    }
}
