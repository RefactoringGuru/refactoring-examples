package refactoring_guru.patterns.abstract_factory.examples.button;

public class OSXButton implements Button {

    @Override
    public void paint() {
        System.out.println("Greeting!");
        System.out.println("You create OSXButton object in your operation system");
    }
}
