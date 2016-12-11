package refactoring_guru.patterns.decorator.example;

import refactoring_guru.patterns.decorator.example.decorators.CompressionDecorator;
import refactoring_guru.patterns.decorator.example.decorators.DataSourceDecorator;
import refactoring_guru.patterns.decorator.example.decorators.EncryptionDecorator;
import refactoring_guru.patterns.decorator.example.decorators.FileDataSource;

import java.io.File;

public class ApplicationConfiguration {
    private boolean enabledEncrypting = false;
    private boolean enabledCompression = false;
    private String salary;
    private File data;

    public ApplicationConfiguration(boolean encrypting, boolean compression, File data) {
        this.enabledEncrypting = encrypting;
        this.enabledCompression = compression;
        this.data = data;
    }

    public void configurationExample() {
        FileDataSource fileSource = new FileDataSource("data.dat");
        DataSourceDecorator source = null;
        if (enabledEncrypting) {
            source = new EncryptionDecorator(fileSource);
        }
        if (enabledCompression) {
            source = new CompressionDecorator(fileSource);
        }
        SalaryManager manager = new SalaryManager(source, data);
        System.out.println(salary = manager.load());
    }
}
