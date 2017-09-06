// EN: Originator class should have a special method, which captures
// originator's state inside a new memento object.
// 
// RU: Класс создателя должен иметь специальный метод, который сохраняет
// состояние создателя в новом объекте-снимке.
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
        // EN: Memento is immutable object; that's why originator passes its
        // state to memento's constructor parameters.
        // 
        // RU: Снимок — неизменяемый объект, поэтому Создатель передаёт все своё
        // состояние через параметры конструктора.
        return new EditorState(this, text, cursorX, cursorY, selectionWidth)

// EN: Memento stores past state of the editor.
// 
// RU: Снимок хранит прошлое состояние редактора.
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

    // EN: At some point, old editor state can be restored using a
    // memento object.
    // 
    // RU: В нужный момент, владелец снимка может восстановить
    // состояние редактора.
    method restore() is
        editor.setText(text)
        editor.setCursor(cursorX, cursorY)
        editor.selectionWidth(selectionWidth)

// EN: Command object can act as a caretaker. In such case, command gets a
// memento just before it changes the originator's state. When undo is
// requested, it restores originator's state with a memento.
// 
// RU: Опекуном может выступать класс команд (см. паттерн Команда). В этом
// случае, команда сохраняет снимок получателя перед тем, как выполнить
// действие. А при отмене, возвращает получателя в предыдущее состояние.
class Command is
    private field backup: EditorState

    method backup() is
        backup = editor.saveState()

    method undo() is
        if (backup != null)
            backup.restore()
    // EN: ...
    // 
    // RU: ...