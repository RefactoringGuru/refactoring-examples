package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Mediator;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * EN: Concrete components don't talk with each other. They have only one
 *     communication channel–sending requests to the mediator.
 *
 * RU: Конкретные компоненты никак не связаны между собой. У них есть только
 *     один канал общения – через отправку уведомлений посреднику.
 */
public class Filter extends JTextField implements Component {
    private Mediator mediator;
    private ListModel listModel;

    public Filter() {}

    @Override
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

    @SuppressWarnings("unchecked")
    private void searchElements(String s) {
        if (!s.equals("")) {
            ArrayList<Note> notes = new ArrayList<>();
            for (int i = 0; i < listModel.getSize(); i++) {
                notes.add((Note)listModel.getElementAt(i));
            }
            DefaultListModel listModel = new DefaultListModel();
            for (Note note : notes) {
                if (note.getName().contains(s)) {
                    listModel.addElement(note);
                }
            }
            mediator.setElementsList(listModel);
        } else {
            if (listModel != null) {
                mediator.setElementsList(listModel);
            }
        }
    }

    @Override
    public String getName() {
        return "Filter";
    }
}
