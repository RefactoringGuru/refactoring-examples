package refactoring_guru.patterns.state.example.states;

import refactoring_guru.patterns.state.example.ui.Player;

public class ReadyState extends State {

    public ReadyState(Player player) {
        super(player);
    }

    @Override
    public String onLock() {
        player.changeState(new refactoring_guru.patterns.state.example.states.LockedState(player));
        return "Locked...";
    }

    @Override
    public String onPlay() {
        String action = player.startPlayback();
        player.changeState(new refactoring_guru.patterns.state.example.states.PlayingState(player));
        return action;
    }

    @Override
    public String onNext() {
        return "Locked...";
    }

    @Override
    public String onPrevious() {
        return "Locked...";
    }
}
