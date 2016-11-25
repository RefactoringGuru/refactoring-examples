// EN: Abstract command defines the common interface for all concrete commands.
// 
// RU: Абстрактная команда задаёт общий интерфейс для всех команд.
abstract class Command is
    field app: Application
    field editor: Editor
    field backup: text

    constructor Command(app: Application, editor: Editor) is
        this.app = app
        this.editor = editor

    // EN: This abstract command defines undo functionality. To save more
    // complex editor state, you might need to use the Memento pattern.
    // 
    // RU: Эта абстрактная команда содержит простейший механизм отмены. Чтобы
    // сохранять более сложное состояние редактора можно использовать
    // паттерн Снимок.
    method backup() is
        backup = editor.text
        app.history.push(this)

    method undo() is
        editor.text = backup

    // EN: Main command method stays abstract so that each concrete command
    // could provide its own implementation.
    // 
    // RU: Главный метод команды остаётся абстрактным, чтобы каждая конкретная
    // команда определила его по-своему.
    abstract method execute()


// EN: Concrete commands.
// 
// RU: Конкретные команды.
class CopyCommand extends EditorCommand is
    // EN: The copy operation is not saved to the history since it does not
    // change editor's state.
    // 
    // RU: Операция копирования не записывается в историю, так как она не меняет
    // состояние редактора.
    method execute() is
        app.clipboard = editor.getSelection()

class CutCommand extends EditorCommand is
    method execute() is
        // EN: Commands, which change editor's state, will be saved to
        // the history.
        // 
        // RU: Команды, меняющие состояние редактора, добавляют себя в историю.
        backup()
        app.clipboard = editor.getSelection()
        editor.deleteSelection()

class PasteCommand implements Command is
    method execute() is
        backup()
        editor.replaceSelection(app.clipboard)


// EN: Global command history is just a stack.
// 
// RU: Глобальная история команд — это стек.
class CommandHistory is
    history: array of Command

    // EN: Last in...
    // 
    // RU: Последний зашедший...
    method push(c: Command) is
        Push command to the end of history array.

    // EN: ...first out
    // 
    // RU: ...выходит первым.
    method pop():Command is
        Get the most recent command from history.


// EN: Editor class has an actual text editing operations. It plays the role of
// Receiver: all commands end up delegating execution to editor's methods.
// 
// RU: Класс редактора содержит непосредственные операции над текстом. Он
// отыгрывает роль Получателя – команды делегируют ему свои действия.
class Editor is
    field text: string
    field cursorX, cursorY, selectionWidth

    method getSelection() is
        Return selected text.

    method deleteSelection() is
        Delete selected text.

    method replaceSelection(text) is
        Insert clipboard contents at current position.


// EN: Application class configures object relations. It acts as a
// Sender–creates and sends command objects to execute some action.
// 
// RU: Класс приложения настраивает объекты для совместной работы. Он выступает
// в роли Отправителя — создаёт команды, чтобы выполнить какие-то действия.
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

    // EN: Hotkeys handler creates new command object each time it fires. In
    // this case, commands could work with multiple editors, but they have the
    // common clipboard.
    // 
    // RU: При каждом нажатии горячей клавиши создаётся новая команда. Команды
    // могут работать с несколькими редакторами одновременно, но имеют общий
    // буфер обмена.
    method getCopyCommand() is
        return (new CopyCommand(this, activeEditor)).execute()
    method getCutCommand() is
        return (new CutCommand(this, activeEditor)).execute()
    method getPasteCommand() is
        return (new PasteCommand(this, activeEditor)).execute()

    // EN: Take the last command from history and run its undo method. Note that
    // we don't know the type of that command. But we don't have to since
    // command knows how to undo its own actions.
    // 
    // RU: Берём последнюю команду из истории и заставляем её все отменить. Мы
    // не знаем конкретный тип команды, но это и не важно, так как каждая
    // команда знает как отменить своё действие.
    method undo() is
        command = history.pop()
        if (command != null)
            command.undo()
