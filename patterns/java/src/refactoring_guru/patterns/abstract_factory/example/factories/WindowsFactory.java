package refactoring_guru.patterns.abstract_factory.example.factories;

import refactoring_guru.patterns.abstract_factory.example.buttons.WindowsButton;
import refactoring_guru.patterns.abstract_factory.example.checkboxes.WindowsCheckbox;

/**
 * EN: Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 * 
 * RU: Каждая конкретная фабрика знает и создаёт только продукты своей вариации.
 */
public class WindowsFactory implements GUIFactory {

    @Override
    public WindowsButton createButton() {
        return new WindowsButton();
    }

    @Override
    public WindowsCheckbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
