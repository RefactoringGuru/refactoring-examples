using System;
using RefactoringGuru.AbstractFactory.Example.Buttons;
using RefactoringGuru.AbstractFactory.Example.Checkboxes;
using RefactoringGuru.AbstractFactory.Example.Factories;

namespace RefactoringGuru.AbstractFactory.Example.App
{
    /**
     * EN: Factory users don't care which concrete factory they use since they
     * work with factories and products through abstract interfaces.
     * 
     * RU: Код, использующий фабрику, не волнует с какой конкретно фабрикой он
     * работает. Все получатели продуктов работают с продуктами через
     * абстрактный интерфейс.
     */
    class Application
    {
        private IButton button;
        private ICheckbox checkbox;

        public Application(IGuiFactory factory)
        {
            button = factory.CreateButton();
            checkbox = factory.CreateCheckbox();
        }

        public void Paint()
        {
            button.Paint();
            checkbox.Paint();

            Console.ReadKey();
        }
    }
}
 