using System;

namespace Guru.Refactoring.Patterns.FactoryMethod.Example
{
    abstract class Dialog
    {
        public void Render()
        {
            //Render other controls

            Button okButton = CreateButton();
            OnClickButtonArgs closeDialog = new OnClickButtonArgs();
            okButton.OnClick(closeDialog);
            okButton.Render(new Size(), new Position());
        }

        // Фабричный метод.
        public abstract Button CreateButton();
    }
}