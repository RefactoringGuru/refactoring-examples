// RU: Этот паттерн предполагает, что у вас есть несколько семейств продуктов,
// находящихся в отдельных иерархиях классов (Button/Checkbox). Продукты одного
// семейства должны иметь общий интерфейс.
//
// EN: This pattern assumes that you have several families of products,
// structured into separate class hierarchies (Button/Checkbox). All products of
// the same family have common interface.
interface Button is
    method paint()

// RU: Все семейства продуктов имеют одинаковые вариации (OSx/Windows).
//
// EN: All products families have the same varieties (OSx/Windows).
class WinButton implementing Button is
    method paint() is
        Render a button in a Windows style

class OSXButton implementing Button is
    method paint() is
        Render a button in a Mac OS X style


interface Checkbox is
    method paint()

class WinCheckbox implementing Checkbox is
    method paint() is
        Render a checkbox in a Windows style

class OSXCheckbox implementing Checkbox is
    method paint() is
        Render a checkbox in a Mac OS X style


// Абстрактная фабрика знает обо всех (абстрактных) типах продуктов.
interface GUIFactory is
    method createButton():Button
    method createCheckbox():Checkbox


// Каждая конкретная фабрика знает и создаёт только продукты своей вариации.
class WinFactory implementing GUIFactory is
    method createButton():Button is
        return new WinButton
    method createCheckbox():Checkbox is
        return new WinCheckbox

// Несмотря на то что фабрики оперируют конкретными классами, их методы
// возвращают абстрактные типы продуктов. Благодаря этому, фабрики можно
// взаимозаменять, не изменяя клиентский код.
class OSXFactory implementing GUIFactory is
    method createButton():Button is
        return new OSXButton
    method createCheckbox():Checkbox is
        return new OSXCheckbox


// Код, использующий фабрику, не волнует с какой конкретно фабрикой он работает.
// Все получатели продуктов работают с продуктами через абстрактный интерфейс.
class Application is
    constructor Application(factory: GUIFactory) is
        Button button = factory.createButton()
        button.paint()


// Приложение создаёт конкретную фабрику динамически, исходя из конфигурации
// или окружения.
class ApplicationConfigurator is
    method main() is
        Read the configuration file
        If the OS specified in the configuration file is Windows, then
            Construct a WinFactory
            Construct an Application with WinFactory
        else
            Construct an OSXFactory
            Construct an Application with OSXFactory
