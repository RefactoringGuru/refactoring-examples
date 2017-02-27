package refactoring_guru.patterns.strategy.example.strategies;

public class ConcreteStrategySubtract implements Strategy {
    public int algorithm(int a, int b) {
        return a - b;
    }
}
