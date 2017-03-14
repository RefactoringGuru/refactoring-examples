package refactoring_guru.patterns.mediator.example.mediator;

import refactoring_guru.patterns.mediator.example.components.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Editor implements Mediator {
    private Filter filter;
    private AddNote add;
    private DeleteNote delete;
    private Title title;
    private TextBox textBox;
    private Save save;
    private List list;

    public Editor() {
        this.filter = new Filter();
        this.add = new AddNote();
        this.delete = new DeleteNote();
        this.title = new Title();
        this.textBox = new TextBox();
        this.save = new Save();
        DefaultListModel listModel = new DefaultListModel();
        this.list = new List(listModel);

        this.filter.setMediator(this);
        this.add.setMediator(this);
        this.delete.setMediator(this);
        this.title.setMediator(this);
        this.textBox.setMediator(this);
        this.save.setMediator(this);
        this.list.setMediator(this);

        // это перенесу в класс списка
        this.list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                Note note = (Note)list.getSelectedValue();
                if (note != null) {
                    getInfoFromList(note);
                } else {
                    clear();
                }
            }
        });
    }

    public void addNewNote(Note note) {
        title.setText("");
        textBox.setText("");
        list.addElement(note);
    }

    public void deleteNote() {
        list.deleteElement();
    }

    public void getInfoFromList(Note note) {
        title.setText(note.getName());
        textBox.setText(note.getText());
    }

    public void saveChanges() {
        Note note = (Note) list.getSelectedValue();
        note.setName(title.getText());
        note.setText(textBox.getText());
        list.repaint();
    }

    public void markNote() {
        Note note = list.getCurrentElement();
        String name = note.getName();
        if (!name.endsWith("*")) {
            note.setName(note.getName() + "*");
        }
        list.repaint();
    }

    public void clear() {
        title.setText("");
        textBox.setText("");
    }


    // getters
    public Filter getFilter() {
        return filter;
    }

    public List getList() {
        return list;
    }

    public AddNote getAddButton() {
        return add;
    }

    public DeleteNote getDeleteButton() {
        return delete;
    }

    public Save getSaveButton() {
        return save;
    }

    public TextBox getTextBox() {
        return textBox;
    }

    public Title getTitle() {
        return title;
    }
}
