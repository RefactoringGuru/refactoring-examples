package refactoring_guru.patterns.memento.drag_image;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    List<Item> backup;

    public void setBackup(List<Item> list) {
        backup = new ArrayList<>(list);
    }

    public List restore() {
        return backup;
    }
}
