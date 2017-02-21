package refactoring_guru.patterns.strategy.internet_order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Application {
    public static Map<Integer, Integer> priceOnProducts = new HashMap<>();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Order order = new Order();

    static {
        priceOnProducts.put(1, 2200);
        priceOnProducts.put(2, 1850);
        priceOnProducts.put(3, 1100);
        priceOnProducts.put(4, 890);
    }

    public static void choiceProduct() throws IOException {
        while (!order.isClosed()) {
            int cost = 0;
            System.out.print("Select a product:" + "\n" +
                    "1 - Mother board" + "\n" +
                    "2 - Processor" + "\n" +
                    "3 - HDD" + "\n" +
                    "4 - Memory" + "\n");
            int choice = Integer.parseInt(reader.readLine());
            cost = priceOnProducts.get(choice);
            System.out.print("Count: ");
            int count = Integer.parseInt(reader.readLine());
            order.setTotalCost(cost * count);
            System.out.print("Continue shopping? Yes/No:  ");
            String proceed = reader.readLine();
            if (proceed.equals("Yes")) {
                continue;
            } else if (proceed.equals("No")) {
                order.setClosed(true);
            }
        }
        System.out.println("Total: " + order.getTotalCost() + "\n" +
                "Select a Payment Method" + "\n" +
                "1 - PalPay" + "\n" +
                "2 - Credit Card");
        String paymentMethod = reader.readLine();
        if (paymentMethod.equals("1")) {
            order.paymentOrder("PayPal");
        }
        if (paymentMethod.equals("2")) {
            order.paymentOrder("CreditCard");
        }
    }

    public static void main(String[] args) throws IOException {
        choiceProduct();
    }
}
