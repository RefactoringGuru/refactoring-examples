using System;
using System.Text;

namespace RefactoringGuru.TemplateMethod.Example.Networks
{
    /**
     * EN: Base class of social network.
     * 
     * RU: Базовый класс социальной сети.
     */
    abstract class Network
    {
        protected String userName;
        protected String password;

        public Network() { }

        /**
         * EN: Publish the data to whatever network.
         * 
         * RU: Публикация данных в любой сети.
         */
        public bool Post(String message)
        {
            // EN: Authenticate before posting. Every network uses a different
            // authentication method.
            // 
            // RU: Проверка данных пользователя перед постом в соцсеть. Каждая
            // сеть для проверки использует разные методы.
            if (LogIn(this.userName, this.password))
            {
                // EN: Send the post data.
                // 
                // RU: Отправка данных.
                bool result = SendData(Encoding.ASCII.GetBytes(message));
                LogOut();
                return result;
            }
            return false;
        }

        public abstract bool LogIn(String userName, String password);
        public abstract bool SendData(byte[] data);
        public abstract void LogOut();
    }
}
