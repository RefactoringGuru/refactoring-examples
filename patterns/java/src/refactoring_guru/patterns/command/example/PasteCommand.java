package refactoring_guru.patterns.command.example;

public class PasteCommand extends Command {

    public PasteCommand(Application app, Editor editor) {
        super(app, editor);
    }

    @Override
    public String execute() {
        backup();
        editor.replaceSelection(app.clipboard);
        return app.clipboard;
    }
}
