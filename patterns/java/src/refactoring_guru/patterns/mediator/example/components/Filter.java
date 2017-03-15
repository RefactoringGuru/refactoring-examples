package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Mediator;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Filter extends JTextField {
    private Mediator mediator;
    private List list;

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
        if (list == null) {
            list = new List(new DefaultListModel());
            list.setModel(listModel);
        } else {
            list.setModel(listModel);
        }
    }

    public void searchElements(String s) {
        if (!s.equals("")) {
            ArrayList<Note> notes = new ArrayList<>();
            for (int i = 0; i < list.getModel().getSize(); i++) {
                notes.add((Note)list.getModel().getElementAt(i));
            }
            DefaultListModel listModel = new DefaultListModel();
            for (Note note : notes) {
                if (note.getName().startsWith(s)) {
                    listModel.addElement(note);
                }
            }
            List tmp = new List(listModel);
            mediator.setList(tmp);
        } else {
            mediator.setList(list);
        }
    }
}
