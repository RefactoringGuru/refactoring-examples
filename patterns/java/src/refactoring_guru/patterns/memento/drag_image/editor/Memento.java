package refactoring_guru.patterns.memento.drag_image.editor;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    List backup;

    public void setBackup(List list) {
        this.backup = new ArrayList(list);
    }

    public List restore() {
        return backup;
    }
}
