package refactoring_guru.patterns.decorator.example;

import refactoring_guru.patterns.decorator.example.decorators.CompressionDecorator;
import refactoring_guru.patterns.decorator.example.decorators.EncryptionDecorator;

public class Demo {
    public static void main(String[] args) {
//        CompressionDecorator decorator = new CompressionDecorator();
        //EncryptionDecorator decorator = new EncryptionDecorator();
//        decorator.writeData("Hello World!");
        //System.out.println(decorator.readData());
        Application app = new Application();
        app.dumbUsageExample();
    }
}
