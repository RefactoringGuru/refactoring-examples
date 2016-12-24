package refactoring_guru.patterns.decorator.example.decorators;

import java.io.File;

public interface DataSource {
    void writeData(String data);
    String readData();
}
