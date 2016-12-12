package refactoring_guru.patterns.abstract_factory.example.buttons;

/**
 * All products families have the same varieties (OSx/Windows).
 * 
 * This is another variant of a button.
 */
public class WindowsButton implements Button {

    @Override
    public void paint() {
        System.out.println("You create WindowsButton.");
    }
}
