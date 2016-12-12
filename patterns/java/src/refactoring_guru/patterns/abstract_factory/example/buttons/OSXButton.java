package refactoring_guru.patterns.abstract_factory.example.buttons;

/**
 * All products families have the same varieties (OSx/Windows).
 * 
 * This is a variant of a button.
 */
public class OSXButton implements Button {

    @Override
    public void paint() {
        System.out.println("Greeting!");
        System.out.println("You create OSXButton object in your operation system");
    }
}
