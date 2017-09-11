// EN: Handler interface.
// 
// RU: Интерфейс обработчиков.
interface ComponentWithContextualHelp is
    method showHelp() is


// EN: Base class for simple components.
// 
// RU: Базовый класс простых компонентов.
abstract class Component implements ContextualHelp is
    field tooltipText: string

    // EN: Container, which contains component, severs as a following object
    // in chain.
    // 
    // RU: Контейнер, содержащий компонент, служит в качестве следующего
    // звена цепочки.
    protected field container: ContainerComponent

    // EN: Component shows tooltip if there's a help text assigned to it.
    // Otherwise it forwards the call to the container if it exists.
    // 
    // RU: Компонент показывает всплывающую подсказку, если задан текст
    // подсказки. В обратном случае, он перенаправляет запрос контейнеру, если
    // тот существует.
    method showHelp() is
        if (tooltipText != null)
            Show tooltip.
        else
            container.showHelp();


// EN: Containers can contain both simple components and other container as
// children. The chain relations are established here. The class inherits
// showHelp behavior from its parent.
// 
// RU: Контейнеры могут включать в себя как простые компоненты, так и другие
// контейнеры. Здесь формируются связи цепочки. Класс унаследует метод showHelp
// от своего родителя.
abstract class ContainerComponent extends Component is
    protected field children: array of Component

    method add(child) is
        children.add(child)
        child.container = this;


// EN: Primitive components may be fine with default help implementation...
// 
// RU: Примитивные компоненты может устраивать поведение помощи по умолчанию...
class Button extends Component is
    // ...

// EN: But complex components may override the default implementation. If a help
// can not be provided in a new way, the component can always call the base
// implementation (see Component class).
// 
// RU: Но сложные компоненты могут переопределять метод помощь по-своему. Но
// если помощь не может быть предоставлена, компонент вызовет базовую реализацию
// (см. класс Component)
class Panel extends ContainerComponent is
    field modalHelpText: string

    method showHelp() is
        if (modalHelpText != null)
            Show modal window with a help text.
        else
            parent::showHelp()

// EN: ...same as above...
// 
// RU: ...то же, что и выше...
class Dialog extends ContainerComponent is
    field wikiPage: string

    method showHelp() is
        if (wikiPage != null)
            Open a wiki help page.
        else
            parent::showHelp()


// EN: Client code.
// 
// RU: Клиентский код.
class Application is
    // EN: Each application configures the chain differently.
    // 
    // RU: Каждое приложение конфигурирует цепочку по-своему.
    method createUI() is
        dialog = new Dialog("Budget Reports")
        dialog.wikiPage = "http://..."
        panel = new Panel(0, 0, 400, 800)
        panel.modalHelpText = "This panel does..."
        ok = new Button(250, 760, 50, 20, "OK")
        ok.tooltipText = "This is a OK button that..."
        cancel = new Button(320, 760, 50, 20, "Cancel")
        // ...
        panel.add(ok)
        panel.add(cancel)
        dialog.add(panel)

    // EN: Imagine what happens here.
    // 
    // RU: Представьте что здесь произойдёт.
    method onF1KeyPress() is
        component = this.getComponentAtMouseCoords()
        component.showHelp()
