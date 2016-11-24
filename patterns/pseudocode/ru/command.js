// Абстрактная команда задаёт общий интерфейс для всех команд.
abstract class Command is
    field app: Application
    field editor: Editor
    field backup: text

    constructor Command(app: Application, editor: Editor) is
        this.app = app
        this.editor = editor

    // Абстрактная команда содержит простейший механизм отмены. Чтобы сохранять
    // более сложное состояние редактора можно использовать паттерн Снимок.
    method backup() is
        backup = editor.text
        app.history.push(this)

    method undo() is
        editor.text = backup

    // Главный метод команды остаётся абстрактным, чтобы каждая конкретная
    // команда определила его по-своему.
    abstract method execute()


// Конкретные команды.
class CopyCommand extends EditorCommand is
    // Операция копирования не записывается в историю, так как она не меняет
    // состояние редактора.
    method execute() is
        app.clipboard = editor.getSelection()

class CutCommand extends EditorCommand is
    method execute() is
        // Команды, меняющие состояние редактора, добавляют себя в историю.
        backup()
        app.clipboard = editor.getSelection()
        editor.deleteSelection()

class PasteCommand implements Command is
    method execute() is
        backup()
        editor.replaceSelection(app.clipboard)


// Глобальная история команд — это стек.
class CommandHistory is
    history: array of Command

    // Первый зашедший...
    method push(c: Command) is
        Push command to the end of history array.

    // ...выходит последним.
    method pop():Command is
        Get the most recent command from history.


// Класс редактора содержит непосредственные операции над текстом. Он отыгрывает
// роль Получателя – команды делегируют ему свои действия.
class Editor is
    field text: string
    field cursorX, cursorY, selectionWidth

    method getSelection() is
        Return selected text.

    method deleteSelection() is
        Delete selected text.

    method replaceSelection(text) is
        Insert clipboard contents at current position.


// Класс приложения настраивает объекты для совместной работы. Он выступает в
// роли Отправителя — создаёт команды, чтобы выполнить какие-то действия.
class Application is
    field clipboard: string
    field editors: array of Editors
    field activeEditor: Editor
    field history: CommandHistory

    method createUI() is
        onKeyPress("Ctrl+C", this.getCopyCommand);
        onKeyPress("Ctrl+X", this.getCutCommand);
        onKeyPress("Ctrl+V", this.getPasteCommand);
        onKeyPress("Ctrl+Z", this.undo);

    // При каждом нажатии горячей клавиши создаётся новая команда. Команды могут
    // работать с несколькими редакторами одновременно, но имеют общий
    // буфер обмена.
    method getCopyCommand() is
        return (new CopyCommand(this, activeEditor)).execute()
    method getCutCommand() is
        return (new CutCommand(this, activeEditor)).execute()
    method getPasteCommand() is
        return (new PasteCommand(this, activeEditor)).execute()

    // Берём последнюю команду из истории и заставляем её все отменить. Команда
    // сама знает как отменить своё действие.
    method undo() is
        command = history.pop()
        if (command != null)
            command.undo()
