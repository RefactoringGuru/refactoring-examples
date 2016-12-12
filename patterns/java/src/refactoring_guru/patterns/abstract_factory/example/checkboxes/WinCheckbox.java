package refactoring_guru.patterns.abstract_factory.example.checkboxes;

/**
 * EN: All products families have the same varieties (OSx/Windows).
 * 
 * This is another variant of a checkbox.
 * 
 * RU: Все семейства продуктов имеют одинаковые вариации (OSx/Windows).
 * 
 * Вариация чекбокса под Windows.
 */
public class WinCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("Hello");
        System.out.println("You create WinCheckbox object in your operation system");
    }
}
