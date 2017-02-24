package refactoring_guru.patterns.memento.example;

import refactoring_guru.patterns.command.example.editor.Editor;

public class Command {
    private EditorState backup;
    private Editor editor;

    public Command(Editor editor) {
        this.editor = editor;
    }

    public void backup() {
        // TODO: fixme
        // backup = editor.saveState();
    }

    public void undo() {
        if (backup != null) {
            backup.restore();
        }
    }
}
