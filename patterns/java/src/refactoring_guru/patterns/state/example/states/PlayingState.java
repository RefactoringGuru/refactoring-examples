package refactoring_guru.patterns.state.example.states;

import refactoring_guru.patterns.state.example.ui.Player;

public class PlayingState extends State {

    public PlayingState(Player player) {
        super(player);
    }

    @Override
    public String onLock() {
        player.changeState(new LockedState(player));
        return "Stop playing";
    }

    @Override
    public String onPlay() {
        String action = player.stopPlayback();
        player.changeState(new ReadyState(player));
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
