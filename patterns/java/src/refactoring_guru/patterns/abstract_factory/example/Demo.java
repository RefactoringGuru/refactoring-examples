package refactoring_guru.patterns.abstract_factory.example;

import refactoring_guru.patterns.abstract_factory.example.factories.GUIFactory;
import refactoring_guru.patterns.abstract_factory.example.factories.OSXFactory;
import refactoring_guru.patterns.abstract_factory.example.factories.WinFactory;

/**
 * EN: Demo class. Everything comes together here.
 *
 * RU: Демо-класс. Здесь всё сводится воедино.
 */
public class Demo {

    /**
     * EN: Application picks the factory type and creates it in run time (usually at
     * initialization stage), depending on the configuration or
     * environment variables.
     *
     * RU: Приложение выбирает тип и создаёт конкретные фабрики динамически исходя
     * из конфигурации или окружения.
     */
    private static Application configureApplication() {
        Application app;
        GUIFactory factory;
        String osName = System.getProperty("os.name");
        if (osName.equals("OS X")) {
            factory = new WinFactory();
            app = new Application(factory);
        } else {
            factory = new OSXFactory();
            app = new Application(factory);
        }
        return app;
    }

    public static void main(String[] args) {
        Application app = configureApplication();
        app.paint();
    }
}
