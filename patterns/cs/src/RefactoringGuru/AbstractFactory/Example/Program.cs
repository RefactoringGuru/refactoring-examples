using System;
using RefactoringGuru.AbstractFactory.Example.App;
using RefactoringGuru.AbstractFactory.Example.Factories;

namespace RefactoringGuru.AbstractFactory.Example
{
    /**
     * EN: Demo class. Everything comes together here.
     * 
     * RU: Демо-класс. Здесь всё сводится воедино.
     */
    class Program
    {
        /**
         * EN: Application picks the factory type and creates it in run time
         * (usually at initialization stage), depending on the configuration or
         * environment variables.
         * 
         * RU: Приложение выбирает тип и создаёт конкретные фабрики динамически
         * исходя из конфигурации или окружения.
         */
        private static Application ConfigureApplication()
        {
            Application app;
            IGuiFactory factory;
            String osName = System.Environment.GetEnvironmentVariable("OS").ToLower();
            if (osName.Contains("mac"))
            {
                factory = new MacOSFactory();
                app = new Application(factory);
            }
            else
            {
                factory = new WindowsFactory();
                app = new Application(factory);
            }
            return app;
        }

        static void Main(string[] args)
        {
            Application app = ConfigureApplication();
            app.Paint();
        }
    }
}
