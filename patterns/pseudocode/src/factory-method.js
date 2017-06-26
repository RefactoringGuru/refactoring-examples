// EN: The Factory Method pattern is applicable only when there is a
// products hierarchy.
// 
// RU: Паттерн Фабричный Метод применим тогда, когда есть иерархия
// классов продуктов.
interface Button is
    method render()
    method onClick(f)

class WindowsButton implements Button is
    method render(a, b) is
        Create and render a Windows looking button.
    method onClick(f) is
        Bind a native OS click event.

class HTMLButton implements Button is
    method render(a, b) is
        Return an HTML representation of a button.
    method onClick(f) is
        Bind a web browser click event.

// EN: Base factory class. Note that the "factory" is merely a role for the
// class. It should have some core business logic which needs different products
// to be created.
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
    // factory method.
    // 
    // RU: Мы выносим весь код создания продуктов в особый Фабричный метод.
    abstract method createButton()

// EN: Concrete factories extend that method to produce different kinds
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

class ClientApplication is
    field dialog: Dialog

    // EN: Application picks a factory type depending on configuration
    // or environment.
    // 
    // RU: Приложение создаёт определённую фабрику в зависимости от конфигурации
    // или окружения.
    method configure() is
        if (we are in windows environment) then
            dialog = new WindowsDialog()

        if (we are in web environment) then
            dialog = new WebDialog()

    // EN: The client code should work with factories and products through their
    // abstract interfaces. This way it will remain functional even if you add
    // new product types to the program.
    // 
    // RU: Весь остальной клиентский код работает с фабрикой и продуктами только
    // через общий интерфейс, поэтому для него неважно какая фабрика
    // была создана.
    method main() is
        dialog.initialize()
        dialog.render()
