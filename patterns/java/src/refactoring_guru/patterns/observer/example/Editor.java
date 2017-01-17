package refactoring_guru.patterns.observer.example;

import java.io.File;

public class Editor {
    public EventManager events;
    private File file;

    public Editor() {
        this.events = new EventManager();
    }

    public void openFile(String filePath) {
        this.file = new File(filePath);
        events.execute("open", file);
    }

    public void saveFile() {
        this.file = new File("D:/tmp");
        events.execute("save", file);
    }
}
