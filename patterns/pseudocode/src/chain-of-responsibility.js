// EN: Abstract handler interface.
// 
// RU: Абстрактный интерфейс обработчика.
abstract class Component is
    protected field onClick: function

    // EN: Basic handling method. It will be called when no other handler is
    // capable of processing a click.
    // 
    // RU: Базовый обработчик клика. Здесь будут заканчиваться передачи вызова
    // по цепочке.
    method click(x, y) is
        if (onClick != null)
            onClick(context)

    abstract method render(x, y, width, height)


// EN: Concrete component implementation. It inherits basic functionality from
// the abstract handler.
// 
// RU: Конкретная реализация компонента. Он наследует базовую функциональность
// абстрактного обработчика.
class ContainerComponent extends Component is
    // EN: Complex concrete component, which has references to other components.
    // 
    // RU: Расширенный элемент цепочки, который имеет связи с
    // другими компонентами-обработчиками.
    protected field children: array of Component

    method add(child) is
        children.add(child)

    // EN: Component stores its references here.
    // 
    // RU: Здесь формируются связи цепочки.
    method click(x, y) is
        if (onClick != null) then
            onClick(context)
        else if (child.atCoord(x,y))
            child.click(x, y)


// EN: If can't handle a click yourself, then pass it to your child component if
// you have one at a click's coordinate.
// 
// RU: Если не можешь сам обработать клик, передай его своему дочернему
// компоненту, который находится в координате клика.
class Button extends Component is
    method render() is
        Draw a button.

class Panel extends ContainerComponent is
    method render() is
        Draw a panel and its children.


// EN: Client code.
// 
// RU: Клиентский код.
class Application is
    // EN: Each application configures the chain differently.
    // 
    // RU: Каждое приложение конфигурирует цепочку по-своему.
    method createUI() is
        panel = new Panel(0, 0, 400, 800)
        ok = new Button(250, 760, 50, 20, "OK")
        ok.onClick({ ... })
        panel.add(ok);
        cancel = new Button(320, 760, 50, 20, "Cancel")
        cancel.onClick({ ... })
        panel.add(cancel)

    // EN: Imagine what happens here.
    // 
    // RU: Представьте что здесь произойдёт.
    method test() is
        panel.click(251, 761)
