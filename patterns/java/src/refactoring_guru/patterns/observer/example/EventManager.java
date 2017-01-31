package refactoring_guru.patterns.observer.example;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    Map<String, List<EventListener>> listeners = new HashMap<>();
    List<EventListener> open = new ArrayList<>();
    List<EventListener> save = new ArrayList<>();

    public EventManager() {
        this.listeners.put("open", open);
        this.listeners.put("save", save);
    }

    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        int index = users.indexOf(listener);
        users.remove(index);
    }

    public void execute(String eventType, File file) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(file);
        }
    }
}
