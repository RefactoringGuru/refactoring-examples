package refactoring_guru.patterns.decorator.example.decorators;

import java.io.File;

public class DataSourceDecorator implements DataSource {
    DataSource wrappee;

    public DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    @Override
    public void writeData(String data) {
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        return wrappee.readData();
    }
}
