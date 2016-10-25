using System;

namespace Guru.Refactoring.Patterns.FactoryMethod.Example
{
    // Конкретные фабрики переопределяют фабричный метод и возвращают из него
    // свои собственные продукты.
    class WindowsDialog : Dialog
    {
        public override Button CreateButton()
        {
            return new WindowsButton();
        }
    }
}