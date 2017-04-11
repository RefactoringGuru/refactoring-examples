using System;
using System.Collections.Generic;

namespace RefactoringGuru.Strategy.Example.Strategies
{
    /**
     * EN: Concrete strategy. Implements PayPal payment method.
     * 
     * RU: Конкретная стратегия. Реализует оплату корзины интернет магазина через
     * платежную систему PayPal.
     */
    class PayByPayPal : PayStrategy
    {
        private static Dictionary<String, String> DATA_BASE = new Dictionary<string, string>();
        private String email;
        private String password;
        private bool signedIn;

        public PayByPayPal()
        {
            FillDataBase();
        }

        /**
         * EN: Collect customer's data.
         * 
         * RU: Собираем данные от клиента.
         */
        public void CollectPaymentDetails()
        {
            while (!signedIn)
            {
                Console.Write("Enter user email: ");
                email = Console.ReadLine();
                Console.Write("Enter password: ");
                password = Console.ReadLine();
                if (Verify())
                {
                    Console.WriteLine("Data verification was successful");
                }
                else
                {
                    Console.WriteLine("Wrong email or password!");
                }
            }
        }

        private bool Verify()
        {
            SetSignedIn(email.Equals(DATA_BASE[password]));
            return signedIn;
        }

        /**
         * EN: Save customer data for future shopping attempts.
         * 
         * RU: Если клиент уже вошел в систему, то для следующей оплаты данные
         * вводить не придется.
         */
        public bool Pay(int paymentAmount)
        {
            if (signedIn)
            {
                Console.WriteLine("Paying " + paymentAmount + " using PayPal");
                return true;
            }
            else
            {
                return false;
            }
        }

        private void SetSignedIn(bool signedIn)
        {
            this.signedIn = signedIn;
        }

        private void FillDataBase()
        {
            DATA_BASE.Add("amanda1985", "amanda@ya.com");
            DATA_BASE.Add("qwerty", "john@amazon.eu");
        }
    }
}
