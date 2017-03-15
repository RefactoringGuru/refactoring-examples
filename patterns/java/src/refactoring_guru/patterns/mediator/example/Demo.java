package refactoring_guru.patterns.mediator.example;

import refactoring_guru.patterns.mediator.example.mediator.Editor;
import refactoring_guru.patterns.mediator.example.mediator.Mediator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Demo {
    public static void main(String[] args) {
        Mediator mediator = new Editor();
        SwingUtilities.invokeLater(() -> initFrame(mediator));
    }

    public static void initFrame(Mediator mediator) {
        JFrame notes = new JFrame("Notes");
        notes.setSize(570, 280);
        notes.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new LineBorder(Color.BLACK));
        JPanel filterPanel = new JPanel();
        filterPanel.setSize(300, 50);
        filterPanel.add(new JLabel("Filter:"));
        mediator.getFilter().setColumns(20);
        filterPanel.add(mediator.getFilter());
        JPanel listPanel = new JPanel();
        listPanel.add(new JScrollPane(mediator.getList()));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(mediator.getAddButton());
        buttonsPanel.add(mediator.getDeleteButton());
        left.add(filterPanel);
        left.add(listPanel);
        left.add(buttonsPanel);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(new LineBorder(Color.BLACK));
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(new JLabel("Title:"));
        mediator.getTitle().setColumns(20);
        titlePanel.add(right.add(mediator.getTitle()));
        JPanel textBoxPanel = new JPanel();
        textBoxPanel.setLayout(new BoxLayout(textBoxPanel, BoxLayout.Y_AXIS));
        textBoxPanel.add(new JLabel("Text:"));
        mediator.getTextBox().setColumns(20);
        mediator.getTextBox().setRows(10);
        mediator.getTextBox().setLineWrap(true);
        mediator.getTextBox().setWrapStyleWord(true);
        mediator.getTextBox().setTabSize(10);
        textBoxPanel.add(new JScrollPane(mediator.getTextBox()));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(mediator.getSaveButton());
        right.add(titlePanel);
        right.add(textBoxPanel);
        right.add(buttonPanel);

        notes.setLayout(new GridLayout(1, 2));
        notes.getContentPane().add(left);
        notes.getContentPane().add(right);
        notes.setResizable(false);
        notes.pack();
        notes.setVisible(true);
        notes.setLocationRelativeTo(null);
    }
}
