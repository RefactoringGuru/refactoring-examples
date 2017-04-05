namespace RefactoringGuru.FactoryMethod.Example.Buttons
{
    /**
     * EN: Common interface for all buttons.
     * 
     * RU: Общий интерфейс для всех продуктов.
     */
    interface IButton
    {
        void Render();
        void OnClick();
    }
}
