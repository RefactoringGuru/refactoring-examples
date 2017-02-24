package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Mediator;

public class Button extends Component{
    public Button(Mediator mediator) {
        super(mediator);
    }
}
