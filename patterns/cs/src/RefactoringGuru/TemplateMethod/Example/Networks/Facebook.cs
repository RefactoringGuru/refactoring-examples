using System;
using System.Text;
using System.Threading;

namespace RefactoringGuru.TemplateMethod.Example.Networks
{
    /**
     * EN: Class of social network
     * 
     * RU: Класс социальной сети.
     */
    class Facebook : Network
    {
        public Facebook(String userName, String password)
        {
            this.userName = userName;
            this.password = password;
        }

        public override bool LogIn(string userName, string password)
        {
            Console.WriteLine("\nChecking user's parameters");
            Console.WriteLine("Name: " + this.userName);
            Console.Write("Password: ");
            for (int i = 0; i < this.password.Length; i++)
            {
                Console.Write("*");
            }
            SimulateNetworkLatency();
            Console.WriteLine("\n\nLogIn success on Facebook");
            return true;
        }

        public override bool SendData(byte[] data)
        {
            bool messagePosted = true;
            if (messagePosted)
            {
                Console.WriteLine("Message: '" + Encoding.UTF8.GetString(data) + "' was posted on Facebook");
                return true;
            }
            else
            {
                return false;
            }
        }

        public override void LogOut()
        {
            Console.WriteLine("User: '" + userName + "' was logged out from Facebook");
        }

        private void SimulateNetworkLatency()
        {
            int i = 0;
            Console.WriteLine();
            while (i < 10)
            {
                Console.Write(".");
                Thread.Sleep(500);
                i++;
            }
        }
    }
}
