namespace RefactoringGuru.Strategy.Example.Strategies
{
    /**
     * EN: Common interface for all strategies.
     * 
     * RU: Общий интерфейс всех стратегий.
     */
    interface PayStrategy
    {
        bool Pay(int paymentAmount);
        void CollectPaymentDetails();
    }
}
