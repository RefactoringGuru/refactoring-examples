package refactoring_guru.patterns.state.example.states;

import refactoring_guru.patterns.state.example.ui.Player;

public class LockedState extends State {

    public LockedState(Player player) {
        super(player);
    }

    @Override
    public String onLock() {
        if (player.isPlaying()) {
            player.changeState(new PlayingState(player));
            return "Stop playing";
        } else {
            player.changeState(new ReadyState(player));
            return "Paused...";
        }
    }

    @Override
    public String onPlay() {
        return null;
    }

    @Override
    public String onNext() {
        return null;
    }

    @Override
    public String onPrevious() {
        return null;
    }
}
