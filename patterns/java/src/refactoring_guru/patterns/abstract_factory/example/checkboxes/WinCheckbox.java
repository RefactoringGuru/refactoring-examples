package refactoring_guru.patterns.abstract_factory.example.checkboxes;

public class WinCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("Hello");
        System.out.println("You create WinCheckbox object in your operation system");
    }
}
