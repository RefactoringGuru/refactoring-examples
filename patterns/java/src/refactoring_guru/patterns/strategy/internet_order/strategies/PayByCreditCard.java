package refactoring_guru.patterns.strategy.internet_order.strategies;

import java.io.*;

/**
 * RU: Конкретная стратегия, реализует оплату корзины интернет магазина
 * кредитной картой клиента.
 */
public class PayByCreditCard implements PayStrategy {
    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private CreditCard card;

    /**
     * RU: Проверяем карту клиента.
     */
    @Override
    public void collectPaymentDetails() {
        try {
            System.out.print("Enter card number: ");
            String number = READER.readLine();
            System.out.print("Enter date 'mm/yy': ");
            String date = READER.readLine();
            System.out.print("Enter cvv code: ");
            String cvv = READER.readLine();
            card = new CreditCard(number, date, cvv);

            // RU: Валидируем номер карты.

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * RU: После проверки карты мы можем оплатить покупку,
     * если клиент продолжает покупки, мы не запрашиваем карту заново.
     */
    @Override
    public boolean pay(int paymentAmount) {
        if (cardIsPresent()) {
            System.out.println("Paying " + paymentAmount + " using Credit Card");
            card.setAmount(card.getAmount() - paymentAmount);
            return true;
        } else {
            return false;
        }
    }

    private boolean cardIsPresent() {
        return card != null;
    }
}

