package refactoring_guru.patterns.strategy.sort_strategy;

import java.util.Arrays;

public class SelectionSort implements SortStrategy {
    @Override
    public void sort(int[] numbers) {
        int position;
        int tmpValue;
        for (int i = numbers.length - 1; i > 0; i--) {
            position = 0;
            for (int j = 1; j <= i; j++) {
                if (numbers[j] > numbers[position]) {
                    position = j;
                }
            }
            tmpValue = numbers[position];
            numbers[i] = tmpValue;
        }
        System.out.println(Arrays.toString(numbers));
    }
}
