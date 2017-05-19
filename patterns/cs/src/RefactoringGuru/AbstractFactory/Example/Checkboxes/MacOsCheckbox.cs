using System;

namespace RefactoringGuru.AbstractFactory.Example.Checkboxes
{
    class MacOsCheckbox : ICheckbox
    {
        /**
         * EN: All products families have the same varieties (MacOS/Windows).
         * 
         * This is a variant of a checkbox.
         * 
         * RU: Все семейства продуктов имеют одинаковые вариации
         * (MacOS/Windows).
         * 
         * Вариация чекбокса под MacOS.
         */
        public void Paint()
        {
            Console.WriteLine("You have created MacOSCheckbox.");
        }
    }
}
