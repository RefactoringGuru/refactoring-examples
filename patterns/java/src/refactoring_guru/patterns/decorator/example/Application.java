package refactoring_guru.patterns.decorator.example;

import refactoring_guru.patterns.decorator.example.decorators.*;

public class Application {
    private boolean enabledEncrypting;
    private boolean enabledCompression;

    public Application(boolean encrypting, boolean compression) {
        this.enabledEncrypting = encrypting;
        this.enabledCompression = compression;
    }

    public void start() {
        DataSource source = new FileDataSource("D:/salary.dat");
        if (enabledEncrypting) {
            source = new EncryptionDecorator(source);
        }
        if (enabledCompression) {
            source = new CompressionDecorator(source);
        }
        SalaryManager manager = new SalaryManager(source);
        manager.save();
        System.out.println(manager.load());
    }


}
