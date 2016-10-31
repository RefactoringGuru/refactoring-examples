package refactoring_guru.patterns.abstract_factory.example.factories;

import refactoring_guru.patterns.abstract_factory.example.buttons.OSXButton;
import refactoring_guru.patterns.abstract_factory.example.checkboxes.OSXCheckbox;

public class OSXFactory implements GUIFactory {

    @Override
    public OSXButton createButton() {
        return new OSXButton();
    }

    @Override
    public OSXCheckbox createCheckbox() {
        return new OSXCheckbox();
    }
}
