// TODO: Придумать пример получше. В этом примере напрашивается решение попроще, без использования стратегии.

package refactoring_guru.patterns.strategy.example;

import refactoring_guru.patterns.strategy.example.context.Context;
import refactoring_guru.patterns.strategy.example.strategies.ConcreteStrategyAdd;
import refactoring_guru.patterns.strategy.example.strategies.ConcreteStrategyMultiply;
import refactoring_guru.patterns.strategy.example.strategies.ConcreteStrategySubtract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DemoClassic {
    public static void main(String[] args) {
        try {
            Context context = new Context();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Input first operand" + "\n" + "a: ");
            int a = Integer.parseInt(reader.readLine());
            System.out.println("Input second operand" + "\n" + "b: ");
            int b = Integer.parseInt(reader.readLine());
            System.out.println("Input action: ");
            String action = reader.readLine();
            if (action.equals("+")) {
                context.setStrategy(new ConcreteStrategyAdd());
            }
            if (action.equals("-")) {
                context.setStrategy(new ConcreteStrategySubtract());
            }
            if (action.equals("*")) {
                context.setStrategy(new ConcreteStrategyMultiply());
            }
            int result = context.executeStrategy(a, b);
            System.out.println("Result = " + result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
