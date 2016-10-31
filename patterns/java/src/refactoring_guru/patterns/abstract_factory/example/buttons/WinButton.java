package refactoring_guru.patterns.abstract_factory.example.buttons;

public class WinButton implements Button {

    @Override
    public void paint() {
        System.out.println("Greeting!");
        System.out.println("You create WinButton object in your operation system");
    }
}
