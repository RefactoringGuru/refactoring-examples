// EN: This pattern assumes that you have a products hierarchy.
// 
// RU: Паттерн предполагает, что у вас есть иерархия классов продуктов.
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

// EN: Base factory class. Note that "factory" is merely a role for the class.
// It should have some core business logic which needs different products to
// be created.
// 
// RU: Базовый класс фабрики. Заметьте, что "фабрика" – это всего лишь
// дополнительная роль для класса. Он уже имеет какую-то бизнес-логику, в
// которой требуется создание разнообразных продуктов.
class Dialog is
    method renderWindow() is
        Render other window controls.

        Button okButton = createButton();
        okButton.onClick(closeDialog);
        okButton.render();

    // EN: Therefore we extract all product creation code to a special
    // Factory method.
    // 
    // RU: Мы выносим весь код создания продуктов в особый Фабричный метод.
    abstract method createButton()

// EN: Concrete factories can extend that method to produce different kinds
// of products.
// 
// RU: Конкретные фабрики переопределяют фабричный метод и возвращают из него
// собственные продукты.
class WindowsDialog extends Dialog is
    method createButton() is
        return new WindowsButton()

class WebDialog extends Dialog is
    method createButton() is
        return new HTMLButton()

class ExampleApplication is
    field dialog: Dialog

    // EN: Application creates picks a factory type depending on configuration
    // or environment.
    // 
    // RU: Приложение создаёт определённую фабрику в зависимости от конфигурации
    // или окружения.
    method configure() is
        if (we are in windows environment) then
            dialog = new WindowsDialog()

        if (we are in web environment) then
            dialog = new WebDialog()

    // EN: All of the client code should work with factories and products
    // through abstract interfaces. This way it does not care which factory it
    // works with and what kind of product it returns.
    // 
    // RU: Весь остальной клиентский код работает с фабрикой и продуктами только
    // через общий интерфейс, поэтому для него неважно какая фабрика
    // была создана.
    method main() is
        dialog.initialize()
        dialog.render()
