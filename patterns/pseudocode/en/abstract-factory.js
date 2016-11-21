// Families of products are structured into separate class hierarchies
// (Button/Checkbox). All products of the same family are implementing
// common interface.
interface Button is
    method paint()

// But different families have related product variations (OSx/Windows).
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


// Abstract factory knows about all (abstract) product types.
interface GUIFactory is
    method createButton():Button
    method createCheckbox():Checkbox


// Each concrete factory knows only about its own variation of concrete products.
class WinFactory implementing GUIFactory is
    method createButton():Button is
        return new WinButton
    method createCheckbox():Checkbox is
        return new WinCheckbox

// Although concrete factories create concrete products, they still return them
// with abstract type. This makes factories interchangeable.
class OSXFactory implementing GUIFactory is
    method createButton():Button is
        return new OSXButton
    method createCheckbox():Checkbox is
        return new OSXCheckbox


// Factory users don't care which concrete factory they use, since they work
// with factories and products through their abstract interfaces.
class Application is
    constructor Application(factory: GUIFactory) is
        Button button = factory.createButton()
        button.paint()


// Application creates concrete factories in run time, depending on the
// configuration or environment variables.
class ApplicationConfigurator is
    method main() is
        Read the configuration file
        If the OS specified in the configuration file is Windows, then
            Construct a WinFactory
            Construct an Application with WinFactory
        else
            Construct an OSXFactory
            Construct an Application with OSXFactory
