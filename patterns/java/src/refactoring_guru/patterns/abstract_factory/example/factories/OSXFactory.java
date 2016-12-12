package refactoring_guru.patterns.abstract_factory.example.factories;

import refactoring_guru.patterns.abstract_factory.example.buttons.OSXButton;
import refactoring_guru.patterns.abstract_factory.example.checkboxes.OSXCheckbox;

/**
 * EN: Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 *
 * RU: Каждая конкретная фабрика знает и создаёт только продукты своей вариации.
 */
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
