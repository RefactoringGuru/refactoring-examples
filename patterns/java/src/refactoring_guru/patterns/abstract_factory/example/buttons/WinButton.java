package refactoring_guru.patterns.abstract_factory.example.buttons;

/**
 * All products families have the same varieties (OSx/Windows).
 * 
 * This is another variant of a button.
 */
public class WinButton implements Button {

    @Override
    public void paint() {
        System.out.println("Greeting!");
        System.out.println("You create WinButton object in your operation system");
    }
}
