package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Mediator;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Filter extends JTextField {
    private Mediator mediator;
    private ListModel listModel;

    public Filter() {}

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    protected void processComponentKeyEvent(KeyEvent keyEvent) {
        String start = getText();
        searchElements(start);
    }

    public void setList(ListModel listModel) {
        this.listModel = listModel;
    }

    public void searchElements(String s) {
        if (!s.equals("")) {
            ArrayList<Note> notes = new ArrayList<>();
            for (int i = 0; i < listModel.getSize(); i++) {
                notes.add((Note)listModel.getElementAt(i));
            }
            DefaultListModel listModel = new DefaultListModel();
            for (Note note : notes) {
                if (note.getName().startsWith(s)) {
                    listModel.addElement(note);
                }
            }
            mediator.setElementsList(listModel);
        } else {
            mediator.setElementsList(listModel);
        }
    }
}
