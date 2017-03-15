package refactoring_guru.patterns.mediator.example.mediator;

import refactoring_guru.patterns.mediator.example.components.*;

import javax.swing.*;

public interface Mediator {
    void addNewNote(Note note);
    void deleteNote();
    void getInfoFromList(Note note);
    void saveChanges();
    void markNote();
    void clear();
    void sendToFilter(ListModel listModel);
    void setList(List list);
    Filter getFilter();
    List getList();
    AddNote getAddButton();
    DeleteNote getDeleteButton();
    Save getSaveButton();
    TextBox getTextBox();
    Title getTitle();
}
