package refactoring_guru.patterns.abstract_factory.examples.factory;


import refactoring_guru.patterns.abstract_factory.examples.button.OSXButton;
import refactoring_guru.patterns.abstract_factory.examples.checkbox.OSXCheckbox;

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
