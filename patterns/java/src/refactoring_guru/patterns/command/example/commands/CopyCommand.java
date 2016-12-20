package refactoring_guru.patterns.command.example.commands;

import refactoring_guru.patterns.command.example.Application;
import refactoring_guru.patterns.command.example.Editor;

public class CopyCommand extends Command {

    public CopyCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    public void execute() {
        app.clipboard = editor.textField.getSelectedText();
    }
}
