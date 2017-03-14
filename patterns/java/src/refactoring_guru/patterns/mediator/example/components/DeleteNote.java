package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Editor;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteNote extends JButton {
    private Editor mediator;

    public DeleteNote() {
        super("Delete note");
    }

    public void setMediator(Editor mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void fireActionPerformed(ActionEvent actionEvent) {
        mediator.deleteNote();
    }
}
