// EN: Common interface for all states.
// 
// RU: Общий интерфейс всех состояний.
abstract class State is
    field player: Player

    // EN: Context passes itself through the state constructor. This may help a
    // state to fetch some useful context data if needed.
    // 
    // RU: Контекст передаёт себя в конструктор состояния, чтобы состояние могло
    // обращаться к его данным и методам в будущем, если потребуется.
    constructor State(player) is
        this.player = player

    abstract method onLock(event)
    abstract method onPlay(event)
    abstract method onNext(event)
    abstract method onPrevious(event)


// EN: Concrete states provide the special implementation for all
// interface methods.
// 
// RU: Конкретные состояния реализуют методы абстрактного состояния по-своему.
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


// EN: They can also trigger state transitions in the context.
// 
// RU: Они также могут переводить контекст в другие состояния.
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


// EN: Player acts as a context.
// 
// RU: Проигрыватель играет роль контекста.
class Player is
    field state: State
    field UI, volume, playlist, currentSong

    constructor Player(player) is
        this.state = new ReadyState(this)
        UI = new UserInterface()

        // EN: Context delegates handling user's input to a state object.
        // Naturally, the outcome will depend on what state is currently active,
        // since all states can handle the input differently.
        // 
        // RU: Контекст заставляет состояние реагировать на пользовательский
        // ввод вместо себя. Реакция может быть разной в зависимости от того,
        // какое состояние сейчас активно.
        UI.lockButton.onClick(state.onLock)
        UI.playButton.onClick(state.onNext)
        UI.nextButton.onClick(state.onNext)
        UI.prevButton.onClick(state.onPrevious)

    // EN: State may call some service methods on the context.
    // 
    // RU: Сервисные методы контекста, вызываемые состояниями.
    method startPlayback() is
        // EN: ...
        // 
        // RU: ...
    method stopPlayback() is
        // EN: ...
        // 
        // RU: ...
    method nextSong() is
        // EN: ...
        // 
        // RU: ...
    method previousSong() is
        // EN: ...
        // 
        // RU: ...
    method fastForward(time) is
        // EN: ...
        // 
        // RU: ...
    method rewind(time) is
        // EN: ...
        // 
        // RU: ...
