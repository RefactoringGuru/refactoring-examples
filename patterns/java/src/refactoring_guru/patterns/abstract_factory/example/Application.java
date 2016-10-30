package refactoring_guru.patterns.abstract_factory.example;

import refactoring_guru.patterns.abstract_factory.example.buttons.Button;
import refactoring_guru.patterns.abstract_factory.example.factories.GUIFactory;

public class Application {
    private Button button;

    public Application(GUIFactory factory) {
        this.button = factory.createButton();
    }

    public Button getButton() {
        return button;
    }
}
