// EN: This pattern assumes that you have several families of products,
// structured into separate class hierarchies (Button/Checkbox). All products of
// the same family have the common interface.
// 
// RU: Этот паттерн предполагает, что у вас есть несколько семейств продуктов,
// находящихся в отдельных иерархиях классов (Button/Checkbox). Продукты одного
// семейства должны иметь общий интерфейс.
interface Button is
    method paint()

// EN: All products families have the same varieties (OSx/Windows).
// 
// RU: Все семейства продуктов имеют одинаковые вариации (OSx/Windows).
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


// EN: Abstract factory knows about all (abstract) product types.
// 
// RU: Абстрактная фабрика знает обо всех (абстрактных) типах продуктов.
interface GUIFactory is
    method createButton():Button
    method createCheckbox():Checkbox


// EN: Each concrete factory extends basic factory and responsible for creating
// products of a single variety.
// 
// RU: Каждая конкретная фабрика знает и создаёт только продукты своей вариации.
class WinFactory implementing GUIFactory is
    method createButton():Button is
        return new WinButton
    method createCheckbox():Checkbox is
        return new WinCheckbox

// EN: Although concrete factories create the concrete products, they still
// return them with the abstract type. This fact makes
// factories interchangeable.
// 
// RU: Несмотря на то что фабрики оперируют конкретными классами, их методы
// возвращают абстрактные типы продуктов. Благодаря этому, фабрики можно
// взаимозаменять, не изменяя клиентский код.
class OSXFactory implementing GUIFactory is
    method createButton():Button is
        return new OSXButton
    method createCheckbox():Checkbox is
        return new OSXCheckbox


// EN: Factory users don't care which concrete factory they use since they work
// with factories and products through abstract interfaces.
// 
// RU: Код, использующий фабрику, не волнует с какой конкретно фабрикой он
// работает. Все получатели продуктов работают с продуктами через
// абстрактный интерфейс.
class Application is
    constructor Application(factory: GUIFactory) is
        Button button = factory.createButton()
        button.paint()


// EN: Application picks the factory type and creates it in run time (usually at
// initialization stage), depending on the configuration or
// environment variables.
// 
// RU: Приложение выбирает тип и создаёт конкретные фабрики динамически исходя
// из конфигурации или окружения.
class ApplicationConfigurator is
    method main() is
        Read the configuration file
        If the OS specified in the configuration file is Windows, then
            Construct a WinFactory
            Construct an Application with WinFactory
        else
            Construct an OSXFactory
            Construct an Application with OSXFactory
