package refactoring_guru.patterns.strategy.sort_strategy;

public class ExampleApplication {
    public static void main(String[] args) {
        int[] numbers = new int[] {-52, 14, 58, 44, 0};
        SortingContext context = new SortingContext();
        context.setStrategy(new InsertionSort());
        context.sort(numbers);
        context.setStrategy(new SelectionSort());
        context.sort(numbers);
    }
}
