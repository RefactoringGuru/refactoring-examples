package refactoring_guru.patterns.state.states;

import refactoring_guru.patterns.state.ui.Player;
import refactoring_guru.patterns.state.ui.UI;

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
        UI.getTextField().setText(player.nextTrack());
        return player.startPlayback();
    }

    @Override
    public String onPrevious() {
        return player.previousTrack();
    }
}
