package refactoring_guru.patterns.abstract_factory.examples;


import refactoring_guru.patterns.abstract_factory.examples.button.Button;
import refactoring_guru.patterns.abstract_factory.examples.factory.GUIFactory;

public class Application {
    private Button button;

    public Application(GUIFactory factory) {
        this.button = factory.createButton();
    }

    public Button getButton() {
        return button;
    }
}
