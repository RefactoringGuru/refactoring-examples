using RefactoringGuru.FactoryMethod.Example.Buttons;

namespace RefactoringGuru.FactoryMethod.Example.Factory
{
    /**
     * EN: HTML Dialog will produce HTML buttons.
     * 
     * RU: HTML-диалог.
     */
    class HtmlDialog : Dialog
    {
        public override IButton CreateButton()
        {
            return new HtmlButton();
        }
    }
}
