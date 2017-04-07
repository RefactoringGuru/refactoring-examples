using System;

namespace RefactoringGuru.AbstractFactory.Example.Checkboxes
{
    /**
     * EN: All products families have the same varieties (MacOS/Windows).
     * 
     * This is another variant of a checkbox.
     * 
     * RU: Все семейства продуктов имеют одинаковые вариации (MacOS/Windows).
     * 
     * Вариация чекбокса под Windows.
     */
    class WindowsCheckbox : ICheckbox
    {
        public void Paint()
        {
            Console.WriteLine("You have created WindowsCheckbox.");
        }
    }
}
