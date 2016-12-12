package refactoring_guru.patterns.abstract_factory.example.checkboxes;

/**
 * EN: All products families have the same varieties (OSx/Windows).
 * 
 * This is a variant of a checkbox.
 * 
 * RU: Все семейства продуктов имеют одинаковые вариации (OSx/Windows).
 * 
 * Вариация чекбокса под OSx.
 */
public class OSXCheckbox implements Checkbox {

    @Override
    public void paint() {
        System.out.println("Hello");
        System.out.println("You create OSXCheckbox object in your operation system");
    }
}
