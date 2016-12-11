package refactoring_guru.patterns.decorator.example.decorators;

import java.io.File;

public interface DataSource {
    File writeData(String data);
    String readData(File file);
}
