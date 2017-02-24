package refactoring_guru.patterns.state.example.states;

import refactoring_guru.patterns.state.example.ui.Player;

public abstract class State {
    public Player player;

    public State(Player player) {
        this.player = player;
    }

    public abstract String onLock();

    public abstract String onPlay();

    public abstract String onNext();

    public abstract String onPrevious();
}
