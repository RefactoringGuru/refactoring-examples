package refactoring_guru.patterns.decorator.example;

import refactoring_guru.patterns.decorator.example.decorators.CompressionDecorator;
import refactoring_guru.patterns.decorator.example.decorators.EncryptionDecorator;
import refactoring_guru.patterns.decorator.example.decorators.FileDataSource;

public class Application {

    public void dumbUsageExample() {
        String salaryRecords = "Den Broun, salary - 1500 usd";
        FileDataSource source = new FileDataSource("data.txt");
        source.writeData(salaryRecords);

        EncryptionDecorator sourceEncrypting = new EncryptionDecorator(source);
        sourceEncrypting.writeData(salaryRecords);

        CompressionDecorator sourceCompression = new CompressionDecorator(source);
        sourceCompression.writeData(sourceEncrypting.getNonDecryptedData());
    }
}
