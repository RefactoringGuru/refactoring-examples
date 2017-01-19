package refactoring_guru.patterns.command.example.commands;

import refactoring_guru.patterns.command.example.Application;
import refactoring_guru.patterns.command.example.Editor;

public class PasteCommand extends Command {

    public PasteCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    public void execute() {
        backup();
        editor.textField.append(app.clipboard);
    }
}
