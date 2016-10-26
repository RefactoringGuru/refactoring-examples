package refactoring_guru.patterns.abstract_factory.examples.factory;


import refactoring_guru.patterns.abstract_factory.examples.button.Button;
import refactoring_guru.patterns.abstract_factory.examples.button.WinButton;
import refactoring_guru.patterns.abstract_factory.examples.checkbox.Checkbox;
import refactoring_guru.patterns.abstract_factory.examples.checkbox.WinCheckbox;

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
