package refactoring_guru.patterns.mediator.example;

import refactoring_guru.patterns.mediator.example.mediator.Editor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Demo {
    public static void main(String[] args) {
        Editor mediator = new Editor();

        JFrame notes = new JFrame("Notes");
        notes.setSize(600, 400);
        notes.setVisible(true);
        notes.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new LineBorder(Color.BLACK));
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter:"), "West");
        mediator.getFilter().setColumns(20);
        filterPanel.add(mediator.getFilter(), "East");
        left.add(filterPanel);

        left.add(new JScrollPane(mediator.getList()));
        left.add(mediator.getAddButton());
        left.add(mediator.getDeleteButton());

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(new LineBorder(Color.BLACK));
        right.add(new JLabel("Title:"));
        mediator.getTitle().setColumns(20);
        right.add(mediator.getTitle());
        right.add(new JLabel("Text:"));
        mediator.getTextBox().setColumns(20);
        mediator.getTextBox().setRows(10);
        mediator.getTextBox().setLineWrap(true);
        mediator.getTextBox().setWrapStyleWord(true);
        right.add(new JScrollPane(mediator.getTextBox()));
        right.add(mediator.getSaveButton());

        notes.setLayout(new GridLayout());
        notes.getContentPane().add(left);
        notes.getContentPane().add(right);
    }
}
