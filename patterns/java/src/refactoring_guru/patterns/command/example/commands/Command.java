package refactoring_guru.patterns.command.example.commands;

import refactoring_guru.patterns.command.example.editor.Editor;

public abstract class Command {
    public Editor editor;
    protected String backup;

    public Command(Editor editor) {
        this.editor = editor;
    }

    protected void backup() {
        backup = editor.textField.getText();
    }

    public void undo() {
        editor.textField.setText(backup);
    }

    public abstract Boolean execute();
}
