package refactoring_guru.patterns.strategy.example.context;

import refactoring_guru.patterns.strategy.example.strategies.Strategy;

public class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int executeStrategy(int a, int b) {
        return strategy.algorithm(a, b);
    }
}
