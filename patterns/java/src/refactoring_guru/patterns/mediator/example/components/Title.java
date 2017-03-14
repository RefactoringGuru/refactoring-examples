package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Mediator;

import javax.swing.*;

public class Title extends JTextField {
    private Mediator mediator;

    public Title() {}

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
}
