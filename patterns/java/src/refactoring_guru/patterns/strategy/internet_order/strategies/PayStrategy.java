package refactoring_guru.patterns.strategy.internet_order.strategies;

/**
 * EN: Common interface for all strategies.
 * 
 * RU: Общий интерфейс всех стратегий.
 */
public interface PayStrategy {
    boolean pay(int paymentAmount);
    void collectPaymentDetails();
}

