using System;

namespace Guru.Refactoring.Patterns.FactoryMethod.Example
{
    class Program
    {
        static ApplicationType AppType = ApplicationType.WebApplication;

        static Dialog dialog;

        // Приложение создаёт определенную фабрику в зависимости от конфигурации
        // или окружения
        static void Configure()
        {
            if (AppType == ApplicationType.WindowsApplication)
            {
                dialog = new WindowsDialog();
            }
            else if(AppType == ApplicationType.WebApplication)
            {
                dialog = new WebDialog();
            }
        }

        static void Main(string[] args)
        {
            Configure();
            dialog.Render();
        }
    }
}
