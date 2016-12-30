package refactoring_guru.patterns.memento.example;

import refactoring_guru.patterns.command.example.Editor;

public class EditorState {
    private Editor editor;
    private String text;
    private int position;
    private int selectionWidth;

    public EditorState(Editor editor) {
        this.editor = editor;
        this.text = editor.textField.getText();
        this.position = editor.textField.getCaretPosition();
        this.selectionWidth = editor.textField.getSelectedText().length();
    }

    public void restore() {
        editor.textField.setText(text);
        editor.textField.setCaretPosition(position);
        editor.textField.select(position, position + selectionWidth);
    }
}
