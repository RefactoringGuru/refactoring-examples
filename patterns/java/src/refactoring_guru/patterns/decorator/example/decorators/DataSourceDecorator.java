package refactoring_guru.patterns.decorator.example.decorators;

import java.io.File;

public class DataSourceDecorator implements DataSource {
    DataSource wrappee;

    public DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    @Override
    public File writeData(String data) {
        return wrappee.writeData(data);
    }

    @Override
    public String readData(File file) {
        return wrappee.readData(file);
    }
}
