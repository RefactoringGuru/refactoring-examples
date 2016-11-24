package refactoring_guru.patterns.strategy.sort_strategy;

public class SortingContext {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) {
        this.strategy = strategy;
    }

    public SortStrategy getStrategy() {
        return strategy;
    }

    public void sort(int[] numbers) {
        strategy.sort(numbers);
    }
}
