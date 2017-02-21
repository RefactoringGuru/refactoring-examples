package refactoring_guru.patterns.strategy.internet_order;

import java.util.*;

// Конкретная стратегия, реализует оплату корзины интернет магазина
// через платежную систему PayPal
public class PayByPayPal implements PayStrategy {
    private static final Map<String, String> DATA_BASE = new HashMap<>();
    private String email;
    private String name;

    static {
        DATA_BASE.put("amanda@ya.com", "Amanda");
        DATA_BASE.put("john@amazon.eu", "John");
    }

    public PayByPayPal(String email, String name) {
        this.email = email;
        this.name = name;
    }


    public boolean verify() {
        return name.equals(DATA_BASE.get(email));
    }

    // Проверяем, есть ли данный клиент в базе системы,
    // если 'да' проводим оплату
    @Override
    public void pay(int paymentAmount) {
        if (verify()) {
            System.out.println("Data verification was successful\n" +
                    "Paying " + paymentAmount + " using PayPal");
        } else {
            System.out.println("Data verification was unsuccessful");
        }
    }
}
