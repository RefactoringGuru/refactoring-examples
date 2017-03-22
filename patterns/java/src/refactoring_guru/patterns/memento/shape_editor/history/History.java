package refactoring_guru.patterns.memento.shape_editor.history;

import refactoring_guru.patterns.memento.shape_editor.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Pair> history = new ArrayList<Pair>();
    private int virtualSize = 0;

    private class Pair {
        Command command;
        Memento memento;
        Pair(Command c, Memento m) {
            command = c;
            memento = m;
        }

        public Command getCommand() {
            return command;
        }

        public Memento getMemento() {
            return memento;
        }
    }

    public void push(Command c, Memento m) {
        if (virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }
        history.add(new Pair(c, m));
        virtualSize = history.size();
    }

    public void undo() {
        Pair pair = getUndo();
        if (pair == null) {
            return;
        }
        System.out.println("Undoing: " + pair.getCommand().getName());
        pair.getMemento().restore();
    }

    public void redo() {
        Pair pair = getRedo();
        if (pair == null) {
            return;
        }
        System.out.println("Redoing: " + pair.getCommand().getName());
        pair.getMemento().restore();
        pair.getCommand().execute();
    }

    public Pair getUndo() {
        if (virtualSize == 0) {
            return null;
        }
        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

    public Pair getRedo() {
        if (virtualSize == history.size()) {
            return null;
        }
        virtualSize = Math.min(history.size(), virtualSize + 1);
        return history.get(virtualSize - 1);
    }
}
