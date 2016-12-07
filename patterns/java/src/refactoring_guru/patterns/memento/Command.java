package refactoring_guru.patterns.memento;

public class Command {
    private EditorState backup;
    private Editor editor;

    public Command(Editor editor) {
        this.editor = editor;
    }

    public void backup() {
        backup = editor.saveState();
    }

    public void undo() {
        if (backup != null) {
            backup.restore();
        }
    }
}
