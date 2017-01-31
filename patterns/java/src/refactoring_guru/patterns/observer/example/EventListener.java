package refactoring_guru.patterns.observer.example;

import java.io.File;

public interface EventListener {
    public void update(File file);
}
