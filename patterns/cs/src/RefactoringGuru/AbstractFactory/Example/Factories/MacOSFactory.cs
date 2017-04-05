﻿using RefactoringGuru.AbstractFactory.Example.Buttons;
using RefactoringGuru.AbstractFactory.Example.Checkboxes;

namespace RefactoringGuru.AbstractFactory.Example.Factories
{
    /**
     * EN: Each concrete factory extends basic factory and responsible for creating
     * products of a single variety.
     * 
     * RU: Каждая конкретная фабрика знает и создаёт только продукты своей вариации.
     */
    class MacOSFactory : IGuiFactory
    {
        public IButton CreateButton()
        {
            return new MacOsButton();
        }

        public ICheckbox CreateCheckbox()
        {
            return new MacOsCheckbox();
        }
    }
}
