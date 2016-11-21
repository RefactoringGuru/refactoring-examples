// Общий интерфейс для всех пультов управления.
interface Remote is
    method power()
    method volumeDown()
    method volumeUp()
    method channelDown()
    method channel()

// Каждая реализация пульта имеет ссылку на устройство, которым управляет.
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

// Вы можете расширять пульт отдельно от устройства.
class AdvancedRemote() extends BasicRemote is
    method Mute() is
        device.setVolume(0)


// Все устройства, с которыми может работать пульт имеют общий интерфейс.
interface Device is
    method isEnabled()
    method enable()
    method disable()
    method getVolume()
    method setVolume(percent)
    method getChannel()
    method setChannel(channel)

// Каждое устройство реализует его по-разному.
class Tv implements Device is
    // ...

class Radio implements Device is
    // ...


// Где-то в клиентском коде.
tv = new Tv();
remote = new Remote(tv)
remote.pover()

radio = new Radio();
remote = new AdvancedRemote(radio)
