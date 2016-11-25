// EN: Common interface for all remotes.
// 
// RU: Общий интерфейс для всех пультов управления.
interface Remote is
    method power()
    method volumeDown()
    method volumeUp()
    method channelDown()
    method channel()

// EN: Each remote has a reference to the device it controls. Methods of the
// remote call methods on the device.
// 
// RU: Каждая реализация пульта имеет ссылку на устройство, которым управляет.
// Методы пульта вызывают методы устройства.
class BasicRemote implements Remote is
    field device: Device
    constructor BasicRemote(device: Device) is
        this.device = device
    method power() is
        if device.isEnabled() then device.disable()
        else device.enable()
    method volumeDown() is
        device.setVolume(device.getVolume() - 10)
    method volumeUp() is
        device.setVolume(device.getVolume() + 10)
    method channelDown() is
        device.setChannel(device.getChannel() - 1)
    method channelUp() is
        device.setChannel(device.getChannel() + 1)

// EN: You can extend remotes independently from devices.
// 
// RU: Вы можете расширять пульт отдельно от устройства.
class AdvancedRemote() extends BasicRemote is
    method Mute() is
        device.setVolume(0)


// EN: All devices have the common interface. This makes them compatible with
// all remotes.
// 
// RU: Все устройства имеют общий интерфейс. Поэтому с ними может работать
// любой пульт.
interface Device is
    method isEnabled()
    method enable()
    method disable()
    method getVolume()
    method setVolume(percent)
    method getChannel()
    method setChannel(channel)

// EN: But each concrete device may have its own implementation.
// 
// RU: Но каждое устройство может иметь особую реализацию.
class Tv implements Device is
    // EN: ...
    // 
    // RU: ...

class Radio implements Device is
    // EN: ...
    // 
    // RU: ...


// EN: Somewhere in client code.
// 
// RU: Где-то в клиентском коде.
tv = new Tv();
remote = new Remote(tv)
remote.pover()

radio = new Radio();
remote = new AdvancedRemote(radio)
