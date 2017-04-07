using System;

namespace RefactoringGuru.ChainOfResponsibility.Example.Middleware
{
    /**
     * RU: Конкретный элемент цепи обрабатывает запрос по-своему.
     */
    class RoleCheckMiddleware : Middleware
    {
        public override bool Check(string email, string password)
        {
            if (email.Equals("admin@example.com"))
            {
                Console.WriteLine("Hello, admin!");
                return true;
            }
            Console.WriteLine("Hello, user!");
            return CheckNext(email, password);
        }
    }
}
