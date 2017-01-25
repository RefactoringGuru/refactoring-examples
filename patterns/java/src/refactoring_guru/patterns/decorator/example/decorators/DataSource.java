package refactoring_guru.patterns.decorator.example.decorators;

public interface DataSource {
    void writeData(String data);

    String readData();
}
