package refactoring_guru.patterns.command.example;

public class Application {
    public String clipboard;
    public Editor activeEditor;
    public CommandHistory history;

    public void createUI() {
        onKeyPress("Ctrl+C", this.getCopyCommand());
        onKeyPress("Ctrl+X", this.getCutCommand());
        onKeyPress("Ctrl+V", this.getPasteCommand());
        onKeyPress("Ctrl+Z", this.undo());
    }

    public String getCopyCommand() {
        Command command = new CopyCommand(this, activeEditor);
        history.push(command);
        return command.execute();
    }

    public String getCutCommand() {
        Command command = new CutCommand(this, activeEditor);
        history.push(command);
        return command.execute();
    }

    public String getPasteCommand() {
        Command command = new PasteCommand(this, activeEditor);
        history.push(command);
        return command.execute();
    }

    public void undo() {
        Command command = history.pop();
        if (command != null) {
            command.undo();
        }
    }
}
