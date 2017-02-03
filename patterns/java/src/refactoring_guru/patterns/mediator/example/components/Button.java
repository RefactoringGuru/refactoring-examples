package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.Mediator;

public class Button extends Component{
    public Button(Mediator mediator) {
        super(mediator);
    }
}
