package refactoring_guru.patterns.decorator.example;

import refactoring_guru.patterns.decorator.example.decorators.CompressionDecorator;
import refactoring_guru.patterns.decorator.example.decorators.DataSourceDecorator;
import refactoring_guru.patterns.decorator.example.decorators.EncryptionDecorator;
import refactoring_guru.patterns.decorator.example.decorators.FileDataSource;

import java.io.*;

public class Application {
    public void dumbUsageExample() {
        String salaryRecords = "100500; 558899; 110215; 254755; 2556658;";
        DataSourceDecorator source = new CompressionDecorator(new EncryptionDecorator(
                new FileDataSource("D:/new file.dat")));
        source.writeData(salaryRecords);
    }
}
