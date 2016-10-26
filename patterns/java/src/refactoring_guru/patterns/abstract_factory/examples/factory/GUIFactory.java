package refactoring_guru.patterns.abstract_factory.examples.factory;

import refactoring_guru.patterns.abstract_factory.examples.button.Button;
import refactoring_guru.patterns.abstract_factory.examples.checkbox.Checkbox;

public interface GUIFactory {
    public abstract Button createButton();
    public abstract Checkbox createCheckbox();
}
