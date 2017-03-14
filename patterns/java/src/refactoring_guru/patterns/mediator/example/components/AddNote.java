package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddNote extends JButton {
    private Editor mediator;

    public AddNote() {
        super("Add note");
    }

    public void setMediator(Editor mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void fireActionPerformed(ActionEvent actionEvent) {
        mediator.addNewNote(new Note());
    }
}
