package refactoring_guru.patterns.command.example;

public class CopyCommand extends Command {

    public CopyCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    public String execute() {
        app.clipboard = editor.getSelection();
        return app.clipboard;
    }
}
