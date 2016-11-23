package refactoring_guru.patterns.composite.example;

import java.awt.*;

public class Demo {
    public static void main(String[] args) {
        ImageEditor editor = new ImageEditor();
        editor.load();
        editor.all.draw();
    }
}
