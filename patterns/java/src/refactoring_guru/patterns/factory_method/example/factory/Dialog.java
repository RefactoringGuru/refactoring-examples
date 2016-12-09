package refactoring_guru.patterns.factory_method.example.factory;

import refactoring_guru.patterns.factory_method.example.buttons.Button;

/**
 * Base factory class. Note that "factory" is merely a role for the class. It
 * should have some core business logic which needs different products to
 * be created.
 */
public abstract class Dialog {

    public void renderWindow() {
        Button okButton = createButton();
        okButton.render();
    }

    /**
     * Subclasses will override this method in order to create specific
     * button objects.
     */
    public abstract Button createButton();
}
