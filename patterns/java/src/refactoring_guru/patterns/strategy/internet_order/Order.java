package refactoring_guru.patterns.strategy.internet_order;

import refactoring_guru.patterns.strategy.internet_order.strategies.PayStrategy;

/**
 * RU: Класс заказа, он ничего не знает о том каким способом(стратегией)
 * будет расчитыватся клиент, а просто вызывает метод оплаты, все
 * остальное стратегия делает сама.
 */
public class Order {
    private static int totalCost = 0;
    private boolean isClosed = false;

    public void processOrder(PayStrategy strategy) {
        strategy.collectPaymentDetails();
    }

    public void setTotalCost(int cost) {
        this.totalCost += cost;
    }

    public static int getTotalCost() {
        return totalCost;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed() {
        isClosed = true;
    }
}

