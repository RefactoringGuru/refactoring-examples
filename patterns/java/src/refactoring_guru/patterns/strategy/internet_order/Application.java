package refactoring_guru.patterns.strategy.internet_order;

import java.io.*;
import java.util.*;

/**
 * RU: Приложение, где и происходит выбор стратегии и оплата заказа
 */
public class Application {
    public static Map<Integer, Integer> priceOnProducts = new HashMap<>();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Order order = new Order();
    private static PayStrategy strategy;

    static {
        priceOnProducts.put(1, 2200);
        priceOnProducts.put(2, 1850);
        priceOnProducts.put(3, 1100);
        priceOnProducts.put(4, 890);
    }

    public static void choiceProduct() throws IOException {
        while (!order.isClosed()) {
            int cost;

            String continueChoice;
            do {
                System.out.print("Select a product:" + "\n" +
                        "1 - Mother board" + "\n" +
                        "2 - CPU" + "\n" +
                        "3 - HDD" + "\n" +
                        "4 - Memory" + "\n");
                int choice = Integer.parseInt(reader.readLine());
                cost = priceOnProducts.get(choice);
                System.out.print("Count: ");
                int count = Integer.parseInt(reader.readLine());
                order.setTotalCost(cost * count);
                System.out.print("You wish to continue selection? Y/N: ");
                continueChoice = reader.readLine();
            } while (continueChoice.equalsIgnoreCase("Y"));

            if (strategy == null) {
                System.out.println("Select a Payment Method" + "\n" +
                        "1 - PalPay" + "\n" +
                        "2 - Credit Card");
                String paymentMethod = reader.readLine();
                if (paymentMethod.equals("1")) {
                    strategy = new PayByPayPal();
                } else if (paymentMethod.equals("2")) {
                    strategy = new PayByCreditCard();
                }
                order.processOrder(strategy);
            }

            System.out.print("Pay " + Order.getTotalCost() + " units or Continue shopping?  P/C: ");
            String proceed = reader.readLine();
            if (proceed.equalsIgnoreCase("P")) {
                strategy.pay(Order.getTotalCost());
                order.setClosed();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        choiceProduct();
    }
}
