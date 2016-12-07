package refactoring_guru.patterns.memento;

public class Editor {
    private String text;
    private int cursorX;
    private int cursorY;
    private int selectionWidth;

    public void setText(String text) {
        this.text = text;
    }

    public void setCursor(int cursorX, int cursorY) {
        this.cursorX = cursorX;
        this.cursorY = cursorY;
    }

    public void setSelectionWidth(int selectionWidth) {
        this.selectionWidth = selectionWidth;
    }

    public EditorState saveState() {
        return new EditorState(this, text, cursorX, cursorY, selectionWidth);
    }
}
