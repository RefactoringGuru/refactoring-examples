using System;
using RefactoringGuru.TemplateMethod.Example.Networks;

namespace RefactoringGuru.TemplateMethod
{
    /**
     * EN: Demo class. Everything comes together here.
     * 
     * RU: Демо-класс. Здесь всё сводится воедино.
     */
    class Program
    {
        static void Main(string[] args)
        {
            Network network = null;
            Console.WriteLine("Input user name: ");
            String userName = Console.ReadLine();
            Console.WriteLine("Input password: ");
            String password = Console.ReadLine();

            // EN: Enter the message.
            // 
            // RU: Вводим сообщение.
            Console.WriteLine("Input message: ");
            String message = Console.ReadLine();

            Console.WriteLine("\nChoose social network for posting message.\n" +
                    "1 - Facebook\n" +
                    "2 - Twitter");
            int choice = int.Parse(Console.ReadLine());

            // EN: Create proper network object and send the message.
            // 
            // RU: Создаем сетевые объекты и публикуем пост.
            if (choice == 1)
            {
                network = new Facebook(userName, password);
            }
            else if (choice == 2)
            {
                network = new Twitter(userName, password);
            }
            network.Post(message);

            Console.ReadKey();
        }
    }
}
