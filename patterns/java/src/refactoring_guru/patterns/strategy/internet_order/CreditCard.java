package refactoring_guru.patterns.strategy.internet_order;

// Класс кредитной карты для более наглядного примера
public class CreditCard {
    private int amount;

    public CreditCard(int amount) {
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
