package refactoring_guru.patterns.command.example;

import refactoring_guru.patterns.command.example.commands.*;

public class Application {
    public String clipboard;
    public Editor activeEditor;

    public CommandHistory history = new CommandHistory();

    public void createUI() {
        activeEditor = new Editor(this);
        activeEditor.init();
    }

    public void getCopyCommand() {
        Command command = new CopyCommand(this, activeEditor);
        history.push(command);
        command.execute();
    }

    public void getCutCommand() {
        Command command = new CutCommand(this, activeEditor);
        history.push(command);
        command.execute();
    }

    public void getPasteCommand() {
        Command command = new PasteCommand(this, activeEditor);
        history.push(command);
        command.execute();
    }

    public void undo() {
        Command command = history.pop();
        if (command != null) {
            command.undo();
        }
    }
}
