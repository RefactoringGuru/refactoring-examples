// Класс издатель.
class EventManager is
    field listeners: hash map of eventTypes and EventListeners

    method subscribe(eventType, listener) is
        listeners.add(eventType, listener)

    method unsubscribe(eventType, listener) is
        listeners.remove(eventType, listener)

    method notify(eventType, a) is
        foreach listeners.of(eventType) as listener
            listener.update(a)

// Конкретный класс издатель, содержащий интересную для других компонентов
// бизнес-логику. Мы могли бы сделать его прямым потомком EventManager, но в
// реальной жизни это не всегда возможно (например, если вам нужно «слушать»
// подкласс). Поэтому здесь мы подключаем механизм подписки при
// помощи композиции.
class Editor is
    field events: EventManager
    field file: File

    constructor Editor() is
        events = new EventManager()

    // Методы бизнес-логики, которые оповещают подписчиков об изменениях.
    method openFile(filename) is
        this.file = new File(filename)
        events.notify('open', filename)

    method saveFile() is
        file.write()
        events.notify('save', file.name)
    // ...


// Интерфейс подписчиков. В современных языках можно обойтись без этого
// интерфейса, подписывая на обновления функции вместо объектов.
interface EventListener is
    method update(a)

// Набор конкретных слушателей. Они реализуют добавочный функциональность,
// реагируя на извещения от издателя.
class LogOpenListener is
    field log: File

    constructor LogOpenListener(filename) is
        this.log = new File(filename)

    method update(filename) is
        log.write("Opened: " + filename)

class EmailNotificationListener is
    field email: string

    constructor EmailNotificationListener(email) is
        this.email = email

    method update(filename) is
        system.email(email, "Someone has changed the file: " + filename)


// Приложение может сконфигурировать издателей и слушателей как угодно, в
// зависимости от целей и конфигурации.
class Application is
    method config() is
        editor = new TextEditor()
        editor.events.subscribe("open",
            new LogOpenListener("/path/to/log/file.txt"))
        editor.events.subscribe("save",
            new EmailNotificationListener("admin@example.com"))
