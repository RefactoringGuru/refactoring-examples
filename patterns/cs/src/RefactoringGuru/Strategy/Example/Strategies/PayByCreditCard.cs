using System;

namespace RefactoringGuru.Strategy.Example.Strategies
{
    /**
     * EN: Concrete strategy. Implements credit card payment method.
     * 
     * RU: Конкретная стратегия. Реализует оплату корзины интернет магазина
     * кредитной картой клиента.
     */
    class PayByCreditCard : PayStrategy
    {
        private CreditCard card;

        /**
         * EN: Collect credit card data.
         * 
         * RU: Собираем данные карты клиента.
         */
        public void CollectPaymentDetails()
        {
            Console.WriteLine("Enter card number: ");
            String number = Console.ReadLine();
            Console.WriteLine("Enter date 'mm/yy': ");
            String date = Console.ReadLine();
            Console.WriteLine("Enter cvv code: ");
            String cvv = Console.ReadLine();
            card = new CreditCard(number, date, cvv);

            // EN: Validate the card number.
            // 
            // RU: Валидируем номер карты.
        }

        /**
         * EN: After card validation we can charge customer's credit card.
         * 
         * RU: После проверки карты мы можем совершить оплату. Если клиент
         * продолжает покупки, мы не запрашиваем карту заново.
         */
        public bool Pay(int paymentAmount)
        {
            if (CardIsPresent())
            {
                Console.WriteLine("Paying " + paymentAmount + " using Credit Card");
                card.SetAmount(card.GetAmount() - paymentAmount);
                return true;
            }
            else
            {
                return false;
            }
        }

        private bool CardIsPresent()
        {
            return card != null;
        }

    }
}
