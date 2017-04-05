using RefactoringGuru.FactoryMethod.Example.Buttons;

namespace RefactoringGuru.FactoryMethod.Example.Factory
{
    /**
     * EN: Windows Dialog will produce Windows buttons.
     * 
     * RU: Диалог на элементах операционной системы.
     */
    class WindowsDialog : Dialog
    {
        public override IButton CreateButton()
        {
            return new WindowsButton();
        }
    }
}
