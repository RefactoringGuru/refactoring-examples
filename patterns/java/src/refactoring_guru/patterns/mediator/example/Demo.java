package refactoring_guru.patterns.mediator.example;

import refactoring_guru.patterns.mediator.example.components.*;
import refactoring_guru.patterns.mediator.example.mediator.*;

import javax.swing.*;

/**
 * EN: Demo class.
 *
 * RU: Демо класс.
 */
public class Demo {
    public static void main(String[] args) {
        Mediator mediator = new Editor();

        mediator.registerComponent(new Title());
        mediator.registerComponent(new TextBox());
        mediator.registerComponent(new AddButton());
        mediator.registerComponent(new DeleteButton());
        mediator.registerComponent(new SaveButton());
        mediator.registerComponent(new List(new DefaultListModel()));
        mediator.registerComponent(new Filter());

        mediator.createGUI();
    }
}
