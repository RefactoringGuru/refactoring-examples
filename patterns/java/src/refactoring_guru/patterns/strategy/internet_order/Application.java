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
            System.out.println("Total: " + order.getTotalCost() + "\n" +
                    "Select a Payment Method" + "\n" +
                    "1 - PalPay" + "\n" +
                    "2 - Credit Card");
            String paymentMethod = reader.readLine();
            if (paymentMethod.equals("1")) {
                if (strategy == null || strategy.getClass().getSimpleName().equals("PayByCreditCard")) {
                    strategy = new PayByPayPal();
                }
            } else if (paymentMethod.equals("2")) {
                if (strategy == null || strategy.getClass().getSimpleName().equals("PayByPayPal")) {
                    strategy = new PayByCreditCard();
                }
            }
            order.processOrder(strategy);

            System.out.print("Continue shopping? Yes/No:  ");
            String proceed = reader.readLine();
            if (proceed.equals("Yes")) {
                order.setToZero();
            } else if (proceed.equals("No")) {
                order.setClosed();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        choiceProduct();
    }
}
