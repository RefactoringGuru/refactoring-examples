package refactoring_guru.patterns.composite.example;

public class Demo {
    public static void main(String[] args) {
        ImageEditor editor = new ImageEditor();
        editor.load();
        editor.groupSelected(editor.all.children.get(0), editor.all.children.get(1), editor.all.children.get(2));
        editor.all.draw();
    }
}
