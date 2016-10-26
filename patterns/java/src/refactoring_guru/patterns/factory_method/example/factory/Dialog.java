package refactoring_guru.patterns.factory_method.example.factory;

import refactoring_guru.patterns.factory_method.example.buttons.Button;

public class Dialog {

    public void renderWindow() {
        Button okButton = createButton();
        okButton.render();
    }

    public Button createButton(){
        return null;
    }
}
