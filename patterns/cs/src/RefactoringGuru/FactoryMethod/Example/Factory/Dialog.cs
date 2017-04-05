using RefactoringGuru.FactoryMethod.Example.Buttons;

namespace RefactoringGuru.FactoryMethod.Example.Factory
{
    /**
     * EN: Base factory class. Note that "factory" is merely a role for the class.
     * It should have some core business logic which needs different products to
     * be created.
     * 
     * RU: Базовый класс фабрики. Заметьте, что "фабрика" – это всего лишь
     * дополнительная роль для класса. Он уже имеет какую-то бизнес-логику, в
     * которой требуется создание разнообразных продуктов.
     */
    abstract class Dialog
    {
        public void RenderWindow()
        {
            // EN: ... other code ...
            // 
            // RU: ... остальной код диалога ...

            IButton okButton = CreateButton();
            okButton.Render();
        }


        /**
         * EN: Subclasses will override this method in order to create specific
         * button objects.
         * 
         * RU: Подклассы будут переопределять этот метод, чтобы создавать конкретные
         * объекты продуктов, разные для каждой фабрики.
         */
        public abstract IButton CreateButton();
    }
}
