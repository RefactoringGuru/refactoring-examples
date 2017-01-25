package refactoring_guru.patterns.command.example.commands;

import refactoring_guru.patterns.command.example.editor.Editor;

public class PasteCommand extends Command {

    public PasteCommand(Editor editor) {
        super(editor);
    }

    @Override
    public Boolean execute() {
        if (editor.clipboard.isEmpty()) return false;

        backup();
        editor.textField.append(editor.clipboard);
        return true;
    }
}
