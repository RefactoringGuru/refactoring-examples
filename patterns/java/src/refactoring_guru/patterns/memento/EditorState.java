package refactoring_guru.patterns.memento;

public class EditorState {
    private Editor editor;
    private String text;
    private int cursorX;
    private int cursorY;
    private int selectionWidth;

    public EditorState(Editor editor, String text, int cursorX, int cursorY, int selectionWidth) {
        this.editor = editor;
        this.text = text;
        this.cursorX = cursorX;
        this.cursorY = cursorY;
        this.selectionWidth = selectionWidth;
    }

    public void restore() {
        editor.setText(text);
        editor.setCursor(cursorX, cursorY);
        editor.setSelectionWidth(selectionWidth);
    }
}
