package refactoring_guru.patterns.mediator.example.mediator;

import refactoring_guru.patterns.mediator.example.components.Component;

public interface Mediator {
    public void notify(String type, Component sender);
}
