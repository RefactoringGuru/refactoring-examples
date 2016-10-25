// Иерархия классов продуктов.
interface Button is
    method render()
    method onClick(f)

class WindowsButton implements Button is
    method render(a, b) is
        Draw a windows style button.
    method onClick(f) is
        Bind native click event.

class HTMLButton implements Button is
    method render(a, b) is
        Return an HTML representation of a button.
    method onClick(f) is
        Bind click event in a web browser.

// Базовый класс фабрики. Заметьте, что фабрика имеет какую-то
// функциональность помимо создания продуктов.
class Dialog is
    method renderWindow() is
        Render other window controls.

        Button okButton = createButton();
        okButton.onClick(closeDialog);
        okButton.render();

    // Фабричный метод.
    abstract method createButton()

// Конкретные фабрики переопределяют фабричный метод и возвращают из него
// свои собственные продукты.
class WindowsDialog extends Application is
    method createButton() is
        return new WindowsButton()

class WebDialog extends Application is
    method createButton() is
        return new HTMLButton()

class ExampleApplication is
    field dialog: Dialog

    // Приложение создаёт определенную фабрику в зависимости от
    // конфигурации или окружения.
    method configure() is
        if (we are in windows environment) then
            dialog = new WindowsDialog()

        if (we are in web environment) then
            dialog = new WebDialog()

    // Весь остальной клиентский код работает с фабрикой и продуктами
    // только через общий интерфейс, поэтому для него не важно какая
    // фабрика была создана.
    method main() is
        dialog.initialize()
        dialog.render()
