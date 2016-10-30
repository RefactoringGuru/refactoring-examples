package refactoring_guru.patterns.abstract_factory.example.checkboxes;

public class OSXCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("Hello");
        System.out.println("You create OSXCheckbox object in your operation system");
    }
}
