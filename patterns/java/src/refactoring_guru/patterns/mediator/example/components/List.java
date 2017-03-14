package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Editor;

import javax.swing.*;

public class List<N> extends JList {
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

    public void deleteElement() {
        int index = this.getSelectedIndex();
        try {
            listModel.remove(index);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("java.lang.ArrayIndexOutOfBoundsException: -1; List.deleteElement(List.java:31)");
        }
    }

    public Note getCurrentElement() {
        return (Note)getSelectedValue();
    }
}
