// Общий интерфейс всех состояний. Контекст передаёт свой объект в
// конструктор состояния, чтобы состояние могло обращаться к его данным и
// методам в будущем, если потребуется.
abstract class State
    field player: Player
    constructor LockedState(player)
        this.player = player
    abstract method onLock(event)
    abstract method onPlay(event)
    abstract method onNext(event)
    abstract method onPrevious(event)

// Конретные состояния реализуют методы абстрактного состояния по-своему.
class LockedState is
    method onLock(event) is
        if (player.playing)
            player.changeState(new PlayingState(player))
        else
            player.changeState(new ReadyState(player))
    method onPlay(event) is
        Do nothing.
    method onNext(event) is
        Do nothing.
    method onPrevious(event) is
        Do nothing.

// Они также могут переводить контекст в другие состояния.
class ReadyState is
    method onLock(event) is
        player.changeState(new LockedState(player))
    method onPlay(event) is
        player.startPlayback();
        player.changeState(new PlayingState(player))
    method onNext(event) is
        player.nextSong();
    method onPrevious(event) is
        player.previousSong();

class PlayingState is
    method onLock(event) is
        player.changeState(new LockedState(player))
    method onPlay(event) is
        player.stopPlayback();
        player.changeState(new ReadyState(player))
    method onNext(event) is
        if (event.doubleclick)
            player.nextSong();
        else
            player.fastForward(5)
    method onPrevious(event) is
        if (event.doubleclick)
            player.previous();
        else
            player.previousSong(5)

// Проигрыватель выступает контекстным объектом.
class Player is
    field state: State
    field UI, volume, playlist, currentSong
    constructor Player(player)
        this.state = new ReadyState(this)
        UI = new UserInterface()

        // Контест заставляет состояние реагировать на пользовательский
        // ввод вместо себя.
        UI.lockButton.onClick(state.onLock)
        UI.playButton.onClick(state.onNext)
        UI.nextButton.onClick(state.onNext)
        UI.prevButton.onClick(state.onPrevious)

    // Сервисные методы контекста, вызываемые состояниями.
    method startPlayback() is
        // ...
    method stopPlayback() is
        // ...
    method nextSong() is
        // ...
    method previousSong() is
        // ...
    method fastForward(time) is
        // ...
    method rewind(time) is
        // ...
