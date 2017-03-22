package refactoring_guru.patterns.memento.shape_editor.history;

import refactoring_guru.patterns.memento.shape_editor.editor.Editor;

public class Memento {
    private String backup;
    private Editor editor;

    public Memento(Editor editor) {
        this.editor = editor;
        this.backup = editor.backup();
    }

    public void restore() {
        editor.restore(backup);
    }
}
