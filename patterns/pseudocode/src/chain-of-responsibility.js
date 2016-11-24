// Базовый интерфейс обработчика.
abstract class Component is
    field onClick: function

    // Базовый метод получения клика, для конечных элементов цепочек.
    method click(x, y) is
        if (onClick != null)
            onClick(context)

    abstract method render(x, y, width, height)


// Расширенный элемент цепочки, который имеет связи с
// другими компонентами-обработчиками.
class ContainerComponent extends Component is
    // Здесь формируются связи цепочки.
    field children: array of Component

    method add(child) is
        children.add(child)

    // Если не можешь сам обработать клик, передай его своему дочернему
    // компоненту, который находится в координате клика.
    method click(x, y) is
        if (onClick != null) then
            onClick(context)
        else if (child.atCoord(x,y))
            child.click(x, y)


// Конкретные реализации компонентов.
class Button extends Component is
    method render() is
        Draw a button.

class Panel extends ContainerComponent is
    method render() is
        Draw a panel and its children.


// Клиентский код.
class Application is
    // Каждое приложение конфигурирует цепочку по-своему.
    method createUI() is
        panel = new Panel(0, 0, 400, 800)
        ok = new Button(250, 760, 50, 20, "OK")
        ok.onClick({ ... })
        panel.add(ok);
        cancel = new Button(320, 760, 50, 20, "Cancel")
        cancel.onClick({ ... })
        panel.add(cancel)

    // Представьте что здесь произойдёт.
    method test() is
        panel.click(251, 761)
