package refactoring_guru.patterns.strategy.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntBinaryOperator;

public class ExampleLambdaApplication {
    private static final Map<String, IntBinaryOperator> OPERATORS;

    static {
        OPERATORS = new HashMap<>();
        OPERATORS.put("+", (x, y) -> x + y);
        OPERATORS.put("-", (x, y) -> x - y);
        OPERATORS.put("*", (x, y) -> x * y);
    }

    public static void main(String[] args) {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Input first operand" + "\n" + "a: ");
            final int a = Integer.parseInt(reader.readLine());
            System.out.println("Input second operand" + "\n" + "b: ");
            final int b = Integer.parseInt(reader.readLine());
            System.out.println("Input action: ");
            final String action = reader.readLine();
            final IntBinaryOperator operator = OPERATORS.get(action);
            if (operator == null) {
                System.out.println("Unknown action: " + action);
            } else {
                final int result = operator.applyAsInt(a, b);
                System.out.println("Result = " + result);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
