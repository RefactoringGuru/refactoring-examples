package refactoring_guru.patterns.strategy.internet_order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * RU: Конкретная стратегия, реализует оплату корзины интернет магазина
 * кредитной картой клиента
 */

public class PayByCreditCard implements PayStrategy {
    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private CreditCard card;

    // RU: Проверяем карту клиента
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
            do{
                int remainderByCC = card.getAmount() - Order.getTotalCost();
                if (remainderByCC < 0) {
                    replenishCard();
                }
            } while (Order.getTotalCost() > card.getAmount());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // RU: после проверки карты мы можем оплатить покипку,
    // если клиент продолжает покипки, мы не запрашиваем нужную карту,
    // а проверяем на наличие средств уже имеющуюся
    @Override
    public void pay(int paymentAmount) {
        if (cardIsPresent()) {
            do {
                if (card.getAmount() < Order.getTotalCost()) {
                    replenishCard();
                }
            } while (card.getAmount() < Order.getTotalCost());
            System.out.println("Paying " + paymentAmount + " using Credit Card");
            card.setAmount(card.getAmount() - paymentAmount);
        } else {
            collectPaymentDetails();
            pay(paymentAmount);
        }
    }

    private void replenishCard() {
        try {
            System.out.println("Balance on the credit card: " + card.getAmount() + "\n" +
                    "Payment amount: " + Order.getTotalCost() + "\n" +
                    "Replenish your balance.");
            int replenish = Integer.parseInt(READER.readLine());
            card.setAmount(replenish);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean cardIsPresent() {
        return card != null;
    }
}
