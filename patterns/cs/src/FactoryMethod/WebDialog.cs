using System;

namespace Guru.Refactoring.Patterns.FactoryMethod.Example
{
    class WebDialog : Dialog
    {
        public override Button CreateButton()
        {
            return new WindowsButton();
        }
    }
}