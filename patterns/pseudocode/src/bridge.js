// EN: All remote classes contain reference to the device they controls.
// Remote's methods delegate most of the work to the device methods.
// 
// RU: Каждый класс пульта имеет ссылку на устройство, которым управляет. Методы
// пульта делегируют работу методам устройства.
class Remote is
    field device: Device
    constructor BasicRemote(device: Device) is
        this.device = device
    method togglePower() is
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

// EN: You can extend remote hierarchy independently from device classes.
// 
// RU: Вы можете расширять классы пультов не трогая код устройств.
class AdvancedRemote extends BasicRemote is
    method mute() is
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
