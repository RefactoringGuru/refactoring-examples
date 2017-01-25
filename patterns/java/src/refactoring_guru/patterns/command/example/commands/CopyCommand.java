package refactoring_guru.patterns.command.example.commands;

import refactoring_guru.patterns.command.example.editor.Editor;

public class CopyCommand extends Command {

    public CopyCommand(Editor editor) {
        super(editor);
    }

    @Override
    public Boolean execute() {
        editor.clipboard = editor.textField.getSelectedText();
        return false;
    }
}
