// Класс создателя реализует метод получения снимка.
class Editor is
    private field text: string
    private field cursorX, cursorY, selectionWidth

    method setText(text) is
        this.text = text

    method setCursor(x, y) is
        this.cursorX = cursorX
        this.cursorY = cursorY

    method selectionWidth(width) is
        this.selectionWidth = width

    method saveState():EditorState is
        // Снимок — неизменяемый объект, поэтому Создатель передаёт все своё
        // состояние через параметры конструктора.
        return new EditorState(this, text, cursorX, cursorY, selectionWidth)

// Снимок хранит прошлое состояние редактора.
class EditorState is
    private field editor: Editor
    private field text: string
    private field cursorX, cursorY, selectionWidth

    constructor EditorState(editor, text, cursorX, cursorY, selectionWidth) is
        this.editor = editor
        this.text = text
        this.cursorX = cursorX
        this.cursorY = cursorY
        this.selectionWidth = selectionWidth

    // В нужный момент, владелец снимка может восстановить состояние редактора.
    method restore() is
        editor.setText(text)
        editor.setCursor(cursorX, cursorY)
        editor.selectionWidth(selectionWidth)

// Клиентом может выступать класс комманд (см. паттерн Команда). В этом случае,
// команда сохраняет снимок получателя перед тем как выполнить действие. А при
// отмене, возвращает получателя в предыдущее состояние.
class Command is
    field backup: EditorState

    method backup() is
        backup = editor.saveState()

    method undo() is
        if (backup != null)
            backup.restore()
    // ...