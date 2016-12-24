package refactoring_guru.patterns.state.states;

import refactoring_guru.patterns.state.ui.Player;

public class ReadyState extends State {

    public ReadyState(Player player) {
        super(player);
    }

    @Override
    public String onLock() {
        player.changeState(new LockedState(player));
        return "Locked...";
    }

    @Override
    public String onPlay() {
        String action = player.startPlayback();
        player.changeState(new PlayingState(player));
        return action;
    }

    @Override
    public String onNext() {
        return player.nextSong();
    }

    @Override
    public String onPrevious() {
        return player.previousSong();
    }
}
