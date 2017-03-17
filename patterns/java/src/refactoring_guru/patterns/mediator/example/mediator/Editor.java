package refactoring_guru.patterns.mediator.example.mediator;

import refactoring_guru.patterns.mediator.example.components.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * EN: Concrete mediator. All chaotic communications between concrete components
 *     have been extracted to the mediator. Now components only talk with the
 *     mediator, which knows who has to handle a request.
 *
 * RU: Конкретный посредник. Все связи между конкретными компонентами переехали
 *     в код посредника. Он получает извещения от своих компонентов и знает как на
 *     них реагировать.
 */
public class Editor implements Mediator {
    private Title title;
    private TextBox textBox;
    private AddButton add;
    private DeleteButton del;
    private SaveButton save;
    private List list;
    private Filter filter;
    private Label label;

    /**
     * EN: Here the registration of components by the mediator.
     *
     * RU: Здесь происходит регистрация компонентов посредником.
     */
    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "AddButton":
                add = (AddButton)component;
                break;
            case "DelButton":
                del = (DeleteButton)component;
                break;
            case "Filter":
                filter = (Filter)component;
                break;
            case "List":
                list = (List)component;
                this.list.addListSelectionListener(listSelectionEvent -> {
                    Note note = (Note)list.getSelectedValue();
                    if (note != null) {
                        getInfoFromList(note);
                    } else {
                        clear();
                    }
                });
                break;
            case "SaveButton":
                save = (SaveButton)component;
                break;
            case "TextBox":
                textBox = (TextBox)component;
                break;
            case "Title":
                title = (Title)component;
                break;
            case "Label":
                label = (Label)component;
                label.setVisible(true);
                break;
        }
    }

    @Override
    public void addNewNote(Note note) {
        title.setText("");
        textBox.setText("");
        list.addElement(note);
    }

    @Override
    public void deleteNote() {
        list.deleteElement();
    }

    @Override
    public void getInfoFromList(Note note) {
        title.setText(note.getName().replace('*', ' '));
        textBox.setText(note.getText());
    }

    @Override
    public void saveChanges() {
        try {
            Note note = (Note) list.getSelectedValue();
            note.setName(title.getText());
            note.setText(textBox.getText());
            list.repaint();
        } catch (NullPointerException ignored) {}
    }

    @Override
    public void markNote() {
        try {
            Note note = list.getCurrentElement();
            String name = note.getName();
            if (!name.endsWith("*")) {
                note.setName(note.getName() + "*");
            }
            list.repaint();
        } catch (NullPointerException ignored) {}
    }

    @Override
    public void clear() {
        title.setText("");
        textBox.setText("");
    }

    @Override
    public void sendToFilter(ListModel listModel) {
        filter.setList(listModel);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setElementsList(ListModel list) {
        this.list.setModel(list);
        this.list.repaint();
    }

    @Override
    public void hideElements(boolean flag) {
        textBox.setVisible(!flag);
        title.setVisible(!flag);
        save.setVisible(!flag);
        label.setVisible(flag);
    }

    @Override
    public void createGUI() {
        JFrame notes = new JFrame("Notes");
        notes.setSize(960, 600);
        notes.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel left = new JPanel();
        left.setBorder(new LineBorder(Color.BLACK));
        left.setSize(300, 300);
        left.setLocation(0, 0);
        left.setLayout(null);
        JPanel filterPanel = new JPanel();
        filterPanel.setSize(290, 50);
        filterPanel.add(new JLabel("Filter:"));
        filter.setColumns(20);
        filterPanel.add(filter);
        filterPanel.setSize(280, 30);
        filterPanel.setLocation(10, 20);
        JPanel listPanel = new JPanel();
        list.setFixedCellWidth(260);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setSize(230, 250);
        listPanel.add(scrollPane);
        listPanel.setSize(290, 150);
        listPanel.setLocation(5, 80);
        add.setSize(80, 25);
        add.setLocation(70, 240);
        del.setSize(80, 25);
        del.setLocation(155, 240);
        left.add(filterPanel);
        left.add(listPanel);
        left.add(add);
        left.add(del);

        JPanel right = new JPanel();
        right.setLayout(null);
        right.setSize(600, 300);
        right.setLocation(300, 0);
        right.setBorder(new LineBorder(Color.BLACK));
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(new JLabel("Title:"));
        titlePanel.setSize(580, 35);
        titlePanel.setLocation(10, 10);
        titlePanel.add(title);
        JPanel textBoxPanel = new JPanel();
        textBoxPanel.setLayout(null);
        JLabel text = new JLabel("Text:");
        text.setSize(50, 20);
        text.setLocation(32, 0);
        textBoxPanel.add(text);
        textBox.setColumns(20);
        textBox.setRows(10);
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        JScrollPane textScroll = new JScrollPane(textBox);
        textScroll.setLocation(0, 30);
        textScroll.setSize(580, 140);
        textBoxPanel.add(textScroll);
        textBoxPanel.setSize(580, 180);
        textBoxPanel.setLocation(10, 55);
        save.setSize(80, 25);
        save.setLocation(510, 240);
        label.setSize(600, 300);
        label.setLocation(0, 0);
        label.setBackground(Color.DARK_GRAY);
        right.add(label);
        right.add(titlePanel);
        right.add(textBoxPanel);
        right.add(save);

        notes.setLayout(null);
        notes.getContentPane().add(left);
        notes.getContentPane().add(right);
        notes.setResizable(false);
        notes.setLocationRelativeTo(null);
        notes.setVisible(true);
    }
}
