using RefactoringGuru.AbstractFactory.Example.Buttons;
using RefactoringGuru.AbstractFactory.Example.Checkboxes;

namespace RefactoringGuru.AbstractFactory.Example.Factories
{
    /**
     * EN: Abstract factory knows about all (abstract) product types.
     * 
     * RU: Абстрактная фабрика знает обо всех (абстрактных) типах продуктов.
     */
    interface IGuiFactory
    {
        IButton CreateButton();
        ICheckbox CreateCheckbox();
    }
}
