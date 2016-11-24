package refactoring_guru.patterns.strategy.sort_strategy;

import java.util.Arrays;

public class InsertionSort implements SortStrategy {
    @Override
    public void sort(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            int tmpValue = numbers[i];
            int j;
            for (j = i - 1; ((j >= 0) && numbers[j] > tmpValue); j--) {
                numbers[j + 1] = numbers[j];
            }
            numbers[j + 1] = tmpValue;
        }
        System.out.println(Arrays.toString(numbers));
    }
}
