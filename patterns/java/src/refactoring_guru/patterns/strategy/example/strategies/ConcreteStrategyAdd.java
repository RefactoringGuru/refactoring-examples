package refactoring_guru.patterns.strategy.example.strategies;

public class ConcreteStrategyAdd implements Strategy {
    public int algorithm(int a, int b) {
        return a + b;
    }
}
