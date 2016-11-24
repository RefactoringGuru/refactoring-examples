package refactoring_guru.patterns.strategy.example;

public class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int a, int b) {
        return strategy.algorithm(a, b);
    }
}
