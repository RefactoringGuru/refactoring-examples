package refactoring_guru.patterns.strategy.internet_order;

/**
 * RU: Общий интерфейс всех стратегий.
 */
public interface PayStrategy {
    void pay(int paymentAmount);
    void collectPaymentDetails();
}
