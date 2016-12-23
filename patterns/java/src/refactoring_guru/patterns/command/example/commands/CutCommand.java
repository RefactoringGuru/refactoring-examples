package refactoring_guru.patterns.command.example.commands;

import refactoring_guru.patterns.command.example.Application;
import refactoring_guru.patterns.command.example.Editor;

public class CutCommand extends Command {

    public CutCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    public void execute() {
        backup();
        String source = editor.textField.getText();
        app.clipboard = editor.textField.getSelectedText();
        editor.textField.setText(weedOutCuttingSymbols(source, app.clipboard));
    }

    public String weedOutCuttingSymbols(String source, String cut) {
        String result = source.substring(0, source.indexOf(cut));
        result += source.substring(source.indexOf(cut) + cut.length(), source.length() - 1);
        return result;
    }
}
