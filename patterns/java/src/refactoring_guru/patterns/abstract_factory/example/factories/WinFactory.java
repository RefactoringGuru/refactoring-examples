package refactoring_guru.patterns.abstract_factory.example.factories;

import refactoring_guru.patterns.abstract_factory.example.buttons.WinButton;
import refactoring_guru.patterns.abstract_factory.example.checkboxes.WinCheckbox;

/**
 * EN: Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 *
 * RU: Каждая конкретная фабрика знает и создаёт только продукты своей вариации.
 */
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
