package refactoring_guru.patterns.mediator.example.mediator;

import refactoring_guru.patterns.mediator.example.components.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Editor implements Mediator {
    private Filter filter;
    private NewNote note;
    private Title title;
    private TextBox textBox;
    private Save save;
    private List list;

    public Editor() {
        this.filter = new Filter();
        this.note = new NewNote();
        this.title = new Title();
        this.textBox = new TextBox();
        this.save = new Save();
        DefaultListModel listModel = new DefaultListModel();
        this.list = new List(listModel);
        list.addElement(new Note());

        this.filter.setMediator(this);
        this.note.setMediator(this);
        this.title.setMediator(this);
        this.textBox.setMediator(this);
        this.save.setMediator(this);
        this.list.setMediator(this);

        // это перенесу в класс списка
        this.list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                Note note = (Note)list.getSelectedValue();
                getInfoFromList(note);
            }
        });
    }

    public void addNewNote(Note note) {
        title.setText("");
        textBox.setText("");
        list.addElement(note);
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


    // getters
    public Filter getFilter() {
        return filter;
    }

    public List getList() {
        return list;
    }

    public NewNote getNoteButton() {
        return note;
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
