package refactoring_guru.patterns.state.example;

import refactoring_guru.patterns.state.example.ui.*;

/**
 * EN: Demo class. Everything comes together here.
 * 
 * RU: Демо-класс. Здесь всё сводится воедино.
 */
public class Demo {
    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}
