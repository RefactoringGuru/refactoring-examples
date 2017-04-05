using System;

namespace RefactoringGuru.FactoryMethod.Example.Buttons
{
    /**
     * EN: HTML button implementation.
     * 
     * RU: Реализация HTML кнопок.
     */
    class HtmlButton : IButton
    {
        public void Render()
        {
            Console.WriteLine("<button>Test Button</button>");
            OnClick();
        }

        public void OnClick()
        {
            Console.WriteLine("Click! Button says - 'Hello World!'");
        }
    }
}
