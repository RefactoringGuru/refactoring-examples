using System;
using RefactoringGuru.ChainOfResponsibility.Example;
using RefactoringGuru.ChainOfResponsibility.Example.Middleware;

namespace RefactoringGuru.ChainOfResponsibility
{
    /**
     * EN: Demo class. Everything comes together here.
     * 
     * RU: Демо-класс. Здесь всё сводится воедино.
     */

    class Program
    {
        private static Server server;

        static void Main(string[] args)
        {
            Init();

            bool success;
            do
            {
                Console.WriteLine("Enter email: ");
                String email = Console.ReadLine();
                Console.WriteLine("Input password: ");
                String password = Console.ReadLine();
                success = server.LogIn(email, password);
            } while (!success);

            Console.ReadKey();
        }


        private static void Init()
        {
            server = new Server();
            server.Register("admin@example.com", "admin_pass");
            server.Register("user@example.com", "user_pass");

            // EN: All checks are linked. Client can build various chains using the
            // same components.
            // 
            // RU: Проверки связаны в одну цепь. Клиент может строить различные
            // цепи, используя одни и те же компоненты.
            Middleware middleware = new ThrottlingMiddleware(2)
                .LinkWith(new UserExistsMiddleware())
                .LinkWith(new RoleCheckMiddleware());

            // EN: Server gets a chain from client code.
            // 
            // RU: Сервер получает цепочку от клиентского кода.
            server.SetMiddleware(middleware);
        }
    }
}