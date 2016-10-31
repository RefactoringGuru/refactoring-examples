package refactoring_guru.patterns.abstract_factory.example.factories;

import refactoring_guru.patterns.abstract_factory.example.buttons.Button;
import refactoring_guru.patterns.abstract_factory.example.checkboxes.Checkbox;

public interface GUIFactory {
    public abstract Button createButton();
    public abstract Checkbox createCheckbox();
}
