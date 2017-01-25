package refactoring_guru.patterns.state.example.ui;

import refactoring_guru.patterns.state.example.states.ReadyState;
import refactoring_guru.patterns.state.example.states.State;

public class Player {
    private State state;
    private boolean playing = false;

    public Player() {
        this.state = new ReadyState(this);
        setPlaying(true);
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isPlaying() {
        return playing;
    }

    public String startPlayback() {
        return "Playing song...";
    }

    public String stopPlayback() {
        return "Stop playing";
    }

    public String nextSong() {
        return "Next song";
    }

    public String previousSong() {
        return "Previous song";
    }
}
