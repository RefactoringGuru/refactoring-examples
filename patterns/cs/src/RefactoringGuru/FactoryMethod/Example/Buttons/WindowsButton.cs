using System;

namespace RefactoringGuru.FactoryMethod.Example.Buttons
{
    /**
     * EN: Windows button implementation.
     * 
     * RU: Реализация нативных кнопок операционной системы.
     */
    class WindowsButton : IButton
    {
        public void Render()
        {
            Console.WriteLine("Windows Button");
            OnClick();
            Console.ReadKey();
        }

        public void OnClick()
        {
            Console.WriteLine("Click! Button says - 'Hello World!'");
        }
    }
}
