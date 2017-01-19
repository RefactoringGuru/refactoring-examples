package refactoring_guru.patterns.decorator.example;

import refactoring_guru.patterns.decorator.example.decorators.*;

public class Demo {
    public static void main(String[] args) {
        dumbUsageExample();
        applicationExample();
    }

    public static void dumbUsageExample() {
        String salaryRecords = "100500; 558899; 110215; 254755; 2556658;";
        DataSourceDecorator source = new CompressionDecorator(new EncryptionDecorator(
                new FileDataSource("D:/new file.dat")));
        source.writeData(salaryRecords);
    }

    public static void applicationExample() {
        Application app = new Application(true, false);
        app.start();
    }
}
