package refactoring_guru.patterns.observer.example;

import java.io.File;
import java.io.IOException;

public class EmailNotificationListener implements EventListener{
    private String email;

    public EmailNotificationListener(String email) {
        this.email = email;
    }

    @Override
    public void update(File file) {
        try {
            System.out.println(email + ", " + "Someone has changed the file: " + file.getCanonicalPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
