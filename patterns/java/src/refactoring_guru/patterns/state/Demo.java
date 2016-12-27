package refactoring_guru.patterns.state;

import refactoring_guru.patterns.state.ui.Player;
import refactoring_guru.patterns.state.ui.UI;

public class Demo {
    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}
