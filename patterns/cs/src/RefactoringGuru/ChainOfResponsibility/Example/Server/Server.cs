using System;
using System.Collections.Generic;

namespace RefactoringGuru.ChainOfResponsibility.Example
{
    class Server
    {
        private static Dictionary<String, String> users = new Dictionary<string, string>();
        private Middleware.Middleware middleware;

        /**
         * EN: Client passes a chain of object to server. This improves flexibility
         * and makes testing the server class easier.
         * 
         * RU: Клиент подаёт готовую цепочку в сервер. Это увеличивает гибкость и
         * упрощает тестирование класса сервера.
         */
        public void SetMiddleware(Middleware.Middleware middleware)
        {
            this.middleware = middleware;
        }

        /**
         * EN: Server gets email and password from client and sends the
         * authorization request to the chain.
         * 
         * RU: Сервер получает email и пароль от клиента и запускает проверку
         * авторизации у цепочки.
         */
        public bool LogIn(String email, String password)
        {
            if (middleware.Check(email, password))
            {
                Console.WriteLine("Authorization have been successful!");

                // EN: Do something useful here for authorized users.
                // 
                // RU: Здесь должен быть какой-то полезный код, работающий для
                // авторизированных пользователей.

                return true;
            }
            return false;
        }

        public void Register(String email, String password)
        {
            users.Add(email, password);
        }

        public static bool HasEmail(String email)
        {
            return users.ContainsKey(email);
        }
    }
}
