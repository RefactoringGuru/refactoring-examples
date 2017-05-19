using System;
using System.Collections.Generic;
using RefactoringGuru.Strategy.Example.Orders;
using RefactoringGuru.Strategy.Example.Strategies;

namespace RefactoringGuru.Strategy
{
    /**
     * EN: World first console e-commerce application.
     * 
     * RU: Первый в мире консольный интерет магазин.
     */
    class Program
    {
        public static Dictionary<int, int> priceOnProducts = new Dictionary<int, int>();
        private static Order order = new Order();
        private static PayStrategy strategy;

        static void Main(string[] args)
        {
            FillProducts();

            while (!order.IsClosed())
            {
                int cost;

                String continueChoice;
                do
                {
                    Console.WriteLine("Select a product:" + "\n" +
                                      "1 - Mother board" + "\n" +
                                      "2 - CPU" + "\n" +
                                      "3 - HDD" + "\n" +
                                      "4 - Memory");
                    int choice = int.Parse(Console.ReadLine());
                    cost = priceOnProducts[choice];
                    Console.Write("Count: ");
                    int count = int.Parse(Console.ReadLine());
                    order.SetTotalCost(cost * count);
                    Console.Write("You wish to continue selection? Y/N: ");
                    continueChoice = Console.ReadLine();
                } while (continueChoice.Equals("y"));

                if (strategy == null)
                {
                    Console.WriteLine("Select a Payment Method" + "\n" +
                                      "1 - PalPay" + "\n" +
                                      "2 - Credit Card");
                    String paymentMethod = Console.ReadLine();

                    // EN: Client creates different strategies based on input
                    // from user, application configuration, etc.
                    // 
                    // RU: Клиент создаёт различные стратегии на основании
                    // пользовательских данных, конфигурации и
                    // прочих параметров.
                    if (paymentMethod.Equals("1"))
                    {
                        strategy = new PayByPayPal();
                    }
                    else if (paymentMethod.Equals("2"))
                    {
                        strategy = new PayByCreditCard();
                    }

                    // EN: Order object delegates gathering payment data to
                    // strategy object, since only strategies know what data
                    // they need to process a payment.
                    // 
                    // RU: Объект заказа делегирует сбор платёжных данны
                    // стратегии, т.к. только стратегии знают какие данные им
                    // нужны для приёма оплаты.
                    order.ProcessOrder(strategy);
                }
                Console.WriteLine("Pay " + Order.GetTotalCost() + " units or Continue shopping?  P/C: ");
                String proceed = Console.ReadLine();
                if (proceed.Equals("p"))
                {
                    // EN: Finally, strategy handles the payment.
                    // 
                    // RU: И наконец, стратегия запускает приём платежа.
                    if (strategy.Pay(Order.GetTotalCost()))
                    {
                        Console.WriteLine("Payment has succeeded");
                    }
                    else
                    {
                        Console.WriteLine("FAIL! Check your data");
                    }
                    order.SetClosed();
                }
            }

            Console.ReadKey();
        }

        private static void FillProducts()
        {
            priceOnProducts.Add(1, 2200);
            priceOnProducts.Add(2, 1850);
            priceOnProducts.Add(3, 1100);
            priceOnProducts.Add(4, 890);
        }
    }
}
