package refactoring_guru.patterns.abstract_factory.example.factories;

import refactoring_guru.patterns.abstract_factory.example.buttons.WinButton;
import refactoring_guru.patterns.abstract_factory.example.checkboxes.WinCheckbox;

public class WinFactory implements GUIFactory {

    @Override
    public WinButton createButton() {
        return new WinButton();
    }

    @Override
    public WinCheckbox createCheckbox() {
        return new WinCheckbox();
    }
}
