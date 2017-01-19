package refactoring_guru.patterns.command.example.commands;

import refactoring_guru.patterns.command.example.Application;
import refactoring_guru.patterns.command.example.Editor;

public abstract class Command {
    public Application app;
    public Editor editor;
    public String backup;

    public Command(Application app, Editor editor) {
        this.app = app;
        this.editor = editor;
    }

    public void backup() {
        backup = app.clipboard;
    }

    public void undo() {
        editor.textField.setText(backup);
    }

    public abstract void execute();
}
