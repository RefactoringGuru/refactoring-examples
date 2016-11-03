package refactoring_guru.patterns.decorator.example.decorators;

public class DataSourceDecorator implements DataSource {
    DataSource wrappee;

    public DataSourceDecorator() {
        wrappee = null;
    }

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

    public void setWrappee(DataSource wrappee) {
        this.wrappee = wrappee;
    }
}
