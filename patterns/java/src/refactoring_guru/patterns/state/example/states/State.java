package refactoring_guru.patterns.state.example.states;

import refactoring_guru.patterns.state.example.ui.Player;

/**
 * EN: Common interface for all states.
 *
 * RU: Общий интерфейс всех состояний.
 */
public abstract class State {
    public Player player;

    /**
     * EN: Context passes itself through the state constructor. This may help a
     * state to fetch some useful context data if needed.
     *
     * RU: Контекст передаёт себя в конструктор состояния, чтобы состояние могло
     * обращаться к его данным и методам в будущем, если потребуется.
     */
    public State(Player player) {
        this.player = player;
    }

    public abstract String onLock();
    public abstract String onPlay();
    public abstract String onNext();
    public abstract String onPrevious();
}
