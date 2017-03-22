package refactoring_guru.patterns.memento.shape_editor.commands;

public interface Command {
    public String getName();
    public void execute();
}
