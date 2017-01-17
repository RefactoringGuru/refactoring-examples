package refactoring_guru.patterns.observer.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogOpenListener implements EventListener {
    private File log;

    public LogOpenListener(String fileName) {
        this.log = new File(fileName);
    }

    public void update(File file) {
        try{
            FileWriter writer = new FileWriter(log);
            writer.write("Opened: " + file.getCanonicalPath());
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
