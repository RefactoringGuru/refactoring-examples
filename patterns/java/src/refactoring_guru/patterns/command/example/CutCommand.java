package refactoring_guru.patterns.command.example;

public class CutCommand extends Command {

    public CutCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    public String execute() {
        backup();
        app.clipboard = editor.getSelection();
        editor.deleteSelection();
        return app.clipboard;
    }
}
