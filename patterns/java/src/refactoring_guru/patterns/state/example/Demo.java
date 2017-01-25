// TODO: Пример норм, но надо добавить кода в стейты, чтобы плеер работал более предсказуемо.

package refactoring_guru.patterns.state.example;

import refactoring_guru.patterns.state.example.ui.Player;
import refactoring_guru.patterns.state.example.ui.UI;

public class Demo {
    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}
