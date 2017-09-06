// EN: Base publisher class. It should include the subscription management code
// and notification methods.
// 
// RU: Базовый класс-издатель. Содержит код управления подписчиками и
// их оповещения.
class EventManager is
    private field listeners: hash map of eventTypes and EventListeners

    method subscribe(eventType, listener) is
        listeners.add(eventType, listener)

    method unsubscribe(eventType, listener) is
        listeners.remove(eventType, listener)

    method notify(eventType, a) is
        foreach listeners.of(eventType) as listener
            listener.update(a)

// EN: Concrete publisher, which contains real business logic interesting for
// some subscribers. We could extend this class from a base publisher, but that
// is not always possible in real life, since a publisher it might already have
// a parent class. In this case, you can patch the subscription logic in with
// composition, just like we did it here.
// 
// RU: Конкретный класс издатель, содержащий интересную для других компонентов
// бизнес-логику. Мы могли бы сделать его прямым потомком EventManager, но в
// реальной жизни это не всегда возможно (например, если вам нужно «слушать»
// подкласс). Поэтому здесь мы подключаем механизм подписки при
// помощи композиции.
class Editor is
    private field events: EventManager
    private field file: File

    constructor Editor() is
        events = new EventManager()

    // EN: Business logic methods can notify subscribers about the changes.
    // 
    // RU: Методы бизнес-логики, которые оповещают подписчиков об изменениях.
    method openFile(filename) is
        this.file = new File(filename)
        events.notify('open', filename)

    method saveFile() is
        file.write()
        events.notify('save', file.name)
    // EN: ...
    // 
    // RU: ...


// EN: Common subscribers interface. By the way, modern programming languages
// allow to simplify this code and use functions as subscribers.
// 
// RU: Общий интерфейс подписчиков. В современных языках можно обойтись без
// этого интерфейса и конкретных слушателей, подписывая на обновления функции
// вместо объектов.
interface EventListener is
    method update(a)

// EN: List of concrete listeners. They react to publisher updates by doing some
// useful work.
// 
// RU: Набор конкретных слушателей. Они реализуют добавочную функциональность,
// реагируя на извещения от издателя.
class LogOpenListener is
    private field log: File

    constructor LogOpenListener(log_filename) is
        this.log = new File(log_filename)

    method update(filename) is
        log.write("Opened: " + filename)

class EmailNotificationListener is
    private field email: string

    constructor EmailNotificationListener(email) is
        this.email = email

    method update(filename) is
        system.email(email, "Someone has changed the file: " + filename)


// EN: Application can configure publishers and subscribers even in run time.
// 
// RU: Приложение может сконфигурировать издателей и слушателей как угодно, в
// зависимости от целей и конфигурации.
class Application is
    method config() is
        editor = new TextEditor()
        editor.events.subscribe("open",
            new LogOpenListener("/path/to/log/file.txt"))
        editor.events.subscribe("save",
            new EmailNotificationListener("admin@example.com"))
