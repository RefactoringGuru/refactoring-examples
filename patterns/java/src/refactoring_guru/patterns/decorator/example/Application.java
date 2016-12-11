package refactoring_guru.patterns.decorator.example;

import refactoring_guru.patterns.decorator.example.decorators.CompressionDecorator;
import refactoring_guru.patterns.decorator.example.decorators.EncryptionDecorator;
import refactoring_guru.patterns.decorator.example.decorators.FileDataSource;

import java.io.File;

public class Application {
    public void dumbUsageExample() {
        String salaryRecords = "Den Broun, salary - 1500 usd";
        FileDataSource source = new FileDataSource("D:\\data.dat");
        source.writeData(salaryRecords);

        EncryptionDecorator sourceEncrypting = new EncryptionDecorator(source);
        CompressionDecorator sourceCompression = new CompressionDecorator(source);

        sourceCompression.writeData(sourceEncrypting.readData(new File("D:\\data.dat")));
        System.out.println(sourceCompression.readData(new File("D:\\archive.zip")));
        System.out.println(sourceEncrypting.readData(new File("D:\\uncompressed_data.dat")));
    }
}
