using RefactoringGuru.Strategy.Example.Strategies;

namespace RefactoringGuru.Strategy.Example.Orders
{
    /**
     * EN: Order class. Doesn't know the concrete payment method (strategy) user has
     * picked. It uses common strategy interface to delegate collecting payment data
     * to strategy object. It can be used to save order to database.
     * 
     * RU: Класс заказа. Ничего не знает о том каким способом (стратегией) будет
     * расчитыватся клиент, а просто вызывает метод оплаты. Все остальное стратегия
     * делает сама.
     */
    class Order
    {
        private static int totalCost = 0;
        private bool isClosed = false;
    
        public void ProcessOrder(PayStrategy strategy)
        {
            strategy.CollectPaymentDetails();
            // EN: Here we could collect and store payment data from the strategy.
            // 
            // RU: Здесь мы могли бы забрать и сохранить платежные данные
            // из стратегии.
        }

        public void SetTotalCost(int cost)
        {
            totalCost += cost;
        }

        public static int GetTotalCost()
        {
            return totalCost;
        }

        public bool IsClosed()
        {
            return isClosed;
        }

        public void SetClosed()
        {
            isClosed = true;
        }
    }
}
