package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.Mediator;

public class Component {
    private Mediator parent;
    private boolean checked = false;

    public Component() {}

    public Component(Mediator parent) {
        this.parent = parent;
    }

    public void click() {
        parent.notify("click", this);
    }

    public void keypress() {
        parent.notify("press", this);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
