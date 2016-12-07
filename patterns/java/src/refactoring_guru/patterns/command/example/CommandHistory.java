package refactoring_guru.patterns.command.example;

import java.util.Stack;

public class CommandHistory {
    Stack history = new Stack();

    public void push(Command c) {
        history.push(c);
    }

    public Command pop() {
        return (Command)history.pop();
    }
}
