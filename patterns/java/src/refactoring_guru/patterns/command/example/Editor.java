package refactoring_guru.patterns.command.example;

public class Editor {
    public String text;
    public int cursorX;
    public int cursorY;
    public int selectionWidth;

    public String getSelection() {
        return text;
    }

    public void deleteSelection() {
        this.text = "";
    }

    public String replaceSelection(String text) {
        return this.text = text;
    }
}
