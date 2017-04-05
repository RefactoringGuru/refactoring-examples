using RefactoringGuru.FactoryMethod.Example.Factory;

namespace RefactoringGuru.FactoryMethod.Example
{
    /**
     * EN: Demo class. Everything comes together here.
     * 
     * RU: Демо-класс. Здесь всё сводится воедино.
     */
    class Program
    {
        private static Dialog dialog;
        static void Main(string[] args)
        {
            Configure();
            RunBusinessLogic();
        }

        /**
         * EN: The concrete factory is usually chosen depending on configuration or
         * environment options.
         * 
         * RU: Приложение создаёт определённую фабрику в зависимости от конфигурации
         * или окружения.
         */
        static void Configure()
        {
            if (System.Environment.GetEnvironmentVariable("OS").Equals("Windows_NT"))
            {
                dialog = new WindowsDialog();
            }
            else
            {
                dialog = new HtmlDialog();
            }
        }


        /**
         * EN: All of the client code should work with factories and products
         * through abstract interfaces. This way it does not care which factory it
         * works with and what kind of product it returns.
         * 
         * RU: Весь остальной клиентский код работает с фабрикой и продуктами только
         * через общий интерфейс, поэтому для него неважно какая фабрика
         * была создана.
         */
        static void RunBusinessLogic()
        {
            dialog.RenderWindow();
        }
    }
}
