package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Mediator;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Filter extends JTextField {
    private Mediator mediator;

    public Filter() {}

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void processComponentKeyEvent(KeyEvent keyEvent) {
        //mediator.markNote();
    }
}
