package refactoring_guru.patterns.strategy.internet_order;

import java.io.*;
import java.util.*;

/**
 * RU: Конкретная стратегия, реализует оплату корзины интернет магазина
 * через платежную систему PayPal
 */
public class PayByPayPal implements PayStrategy {
    private static final Map<String, String> DATA_BASE = new HashMap<>();
    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private String email;
    private String password;
    private boolean signingIn;

    static {
        DATA_BASE.put("amanda1985", "amanda@ya.com");
        DATA_BASE.put("qwerty", "john@amazon.eu");
    }


     // RU: Проверяем данные клиента в базе платежной системы,
     // пример подразумевает что он зарегистрирован
    @Override
    public void collectPaymentDetails() {
        try {
            while (!signingIn) {
                System.out.print("Enter user email: ");
                email = READER.readLine();
                System.out.print("Enter password: ");
                password = READER.readLine();
                if (verify()) {
                    System.out.println("Data verification was successful");
                } else {
                    System.out.println("Wrong email or password!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean verify() {
        setSigningIn(email.equals(DATA_BASE.get(password)));
        return signingIn;
    }

    // RU: Если клиент вошел в систему, то для следующей оплаты
    // данные вводить не придется
    @Override
    public void pay(int paymentAmount) {
        if (signingIn) {
            System.out.println("Paying " + paymentAmount + " using PayPal");
        } else {
            collectPaymentDetails();
            pay(paymentAmount);
        }
    }

    private void setSigningIn(boolean signingIn) {
        this.signingIn = signingIn;
    }
}
