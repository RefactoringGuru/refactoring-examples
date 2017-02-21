package refactoring_guru.patterns.strategy.internet_order;

// Конкретная стратегия, реализует оплату корзины интернет магазина
// редитной картой клиента
public class PayByCreditCard implements PayStrategy {
    private CreditCard card;

    public PayByCreditCard(CreditCard card) {
        this.card = card;
    }

    // Проверяем баланс по карте, если остатка хватает на покупку
    // проводим оплату
    @Override
    public void pay(int paymentAmount) {
        int remainderByCC = card.getAmount() - paymentAmount;
        if (remainderByCC > 0) {
            System.out.println("Paying " + paymentAmount + " using Credit Card");
            card.setAmount(remainderByCC);
        } else {
            System.out.println("The purchase is not made. Low balance on the Credit Card: " + remainderByCC);
        }
    }
}
