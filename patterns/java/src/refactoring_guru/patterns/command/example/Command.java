package refactoring_guru.patterns.command.example;

public abstract class Command {
    public Application app;
    public Editor editor;
    public String backup;

    public Command(Application app, Editor editor) {
        this.app = app;
        this.editor = editor;
    }

    public void backup() {
        backup = editor.text;
        app.history.push(this);
    }

    public void undo() {
        editor.text = backup;
    }

    public abstract String execute();
}
