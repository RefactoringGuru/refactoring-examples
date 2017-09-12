// EN: Abstract command defines the common interface for all concrete commands.
// 
// RU: Абстрактная команда задаёт общий интерфейс для всех команд.
abstract class Command is
    protected field app: Application
    protected field editor: Editor
    protected field backup: text

    // EN: Regular constructor.
    // 
    // RU: Обычный конструктор.
    constructor Command(app: Application, editor: Editor) is
        this.app = app
        this.editor = editor

    // EN: Cloning contructor.
    // 
    // RU: Конструктор клонирования.
    constructor Command(copy: Command) is
        this.app = copy.app
        this.editor = copy.editor
        this.backup = copy.backup

    // EN: This method produces a copy of the command, suitable for saving in
    // history. It's actually the Prototype pattern in action.
    // 
    // RU: Этот метод производит копию команду, которую удобно сохранить в
    // истории. В сущности, здесь мы применяем паттерн Прототип.
    method clone() is
        return new Command(this);

    // EN: A command may backup editor's state prior to executing its action. We
    // must store a copy of the command in history to preserve the backup kept
    // inside. The copy may be used in future to revert editor's state.
    // 
    // RU: Этот метод будет выполнен перед основным действием команды. Он
    // сохраняет состояние редактора внутри команды, а затем добавляет копию
    // команды в историю. Если потребуется отмена, состояние редактора можно
    // будет откатить к занчению, сохраненному в команде.
    method backup() is
        backup = editor.text
        app.history.push(this.clone())

    // EN: Restore editor's state.
    // 
    // RU: Восстанавливаем состояние редактора.
    method undo() is
        editor.text = backup

    // EN: Main command method stays abstract so that each concrete command
    // provide its own implementation.
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

// EN: Undo is also a command.
// 
// RU: Отмена это тоже команда.
class UndoCommand implements Command is
    method execute() is
        editor.undo()


// EN: Global command history is just a stack.
// 
// RU: Глобальная история команд — это стек.
class CommandHistory is
    private field history: array of Command

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

    // EN: Code that assigns commands to UI objects may look like this.
    // 
    // RU: Код, привязывающий команды к элементам интефрейса может выглядеть
    // примерно так.
    method createUI() is
        // ...
        copy = new CopyCommand(this, activeEditor);
        copyButton.setCommand(copy);
        shortcuts.onKeyPress("Ctrl+C", copy);

        cut = new CutCommand(this, activeEditor);
        cutButton.setCommand(cut);
        shortcuts.onKeyPress("Ctrl+X", cut);

        paste = new PasteCommand(this, activeEditor);
        pasteButton.setCommand(paste);
        shortcuts.onKeyPress("Ctrl+V", paste);

        undo = new UndoCommand(this, activeEditor);
        undoButton.setCommand(undo);
        shortcuts.onKeyPress("Ctrl+Z", undo);

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
