package refactoring_guru.patterns.factory_method.example.factory;

import refactoring_guru.patterns.factory_method.example.buttons.Button;

public class Dialog {
    private boolean closeDialog = true;

    public void renderWindow() {
        Button okButton = createButton();
        okButton.render();
        okButton.onClick(closeDialog);
    }

    public Button createButton(){
        return null;
    }
}
