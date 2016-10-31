package refactoring_guru.patterns.abstract_factory.example;

import refactoring_guru.patterns.abstract_factory.example.buttons.Button;
import refactoring_guru.patterns.abstract_factory.example.factories.GUIFactory;
import refactoring_guru.patterns.abstract_factory.example.factories.OSXFactory;
import refactoring_guru.patterns.abstract_factory.example.factories.WinFactory;

public class Demo {
    public static void main(String[] args) {
        String osName = System.getProperty("os.name");
        Application app;
        GUIFactory factory;
        Button button = null;

        if (osName.equals("Windows 10")) {
            factory = new WinFactory();
            app = new Application(factory);
            button = app.getButton();
        } else if (osName.equals("OS X")) {
            factory = new OSXFactory();
            app = new Application(factory);
            button = app.getButton();
        }

        try {
            button.paint();
        } catch (NullPointerException nullException) {
            System.out.println("Oops, your OS is Linux :)");
        }

    }
}
