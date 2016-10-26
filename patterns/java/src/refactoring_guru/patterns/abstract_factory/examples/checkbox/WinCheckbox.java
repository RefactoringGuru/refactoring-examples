package refactoring_guru.patterns.abstract_factory.examples.checkbox;


public class WinCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("Hello");
        System.out.println("You create WinCheckbox object in your operation system");
    }
}
