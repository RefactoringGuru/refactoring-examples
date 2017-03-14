package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Editor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseListener;

public class List extends JList {
    private Editor mediator;
    private DefaultListModel listModel;

    public List(DefaultListModel listModel) {
        super(listModel);
        this.listModel = listModel;
        this.setLayoutOrientation(JList.VERTICAL);
    }

    public void setMediator(Editor mediator) {
        this.mediator = mediator;
    }

    public void addElement(Note note) {
        listModel.addElement(note);
        int index = listModel.size() - 1;
        setSelectedIndex(index);
        ensureIndexIsVisible(index);
    }
}
