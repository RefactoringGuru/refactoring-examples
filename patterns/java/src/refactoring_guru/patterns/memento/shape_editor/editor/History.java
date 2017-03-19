package refactoring_guru.patterns.memento.shape_editor.editor;

import refactoring_guru.patterns.memento.shape_editor.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Command> history = new ArrayList<Command>();
    private int virtualSize = 0;

    public void push(Command c) {
        if (virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }
        history.add(c);
        virtualSize = history.size();
    }

    public Command getUndo() {
        if (virtualSize == 0) {
            return null;
        }
        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

    public Command getRedo() {
        if (virtualSize == history.size()) {
            return null;
        }
        virtualSize = Math.min(history.size(), virtualSize + 1);
        return history.get(virtualSize - 1);
    }
}
