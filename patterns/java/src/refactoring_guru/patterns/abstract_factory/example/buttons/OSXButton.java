package refactoring_guru.patterns.abstract_factory.example.buttons;

/**
 * All products families have the same varieties (OSx/Windows).
 * 
 * This is a variant of a button.
 */
public class OSXButton implements Button {

    @Override
    public void paint() {
        System.out.println("You created OSXButton.");
    }
}
