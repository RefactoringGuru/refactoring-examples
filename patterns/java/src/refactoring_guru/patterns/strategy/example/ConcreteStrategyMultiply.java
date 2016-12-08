package refactoring_guru.patterns.strategy.example;

public class ConcreteStrategyMultiply implements Strategy {
    @Override
    public int algorithm(int a, int b) {
        return a * b;
    }
}
