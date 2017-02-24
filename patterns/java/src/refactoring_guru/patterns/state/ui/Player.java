package refactoring_guru.patterns.state.ui;

import refactoring_guru.patterns.state.states.*;

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
        return "Playing track...";
    }

    public String nextTrack() {
        return "Playing next track...";
    }

    public String previousTrack() {
        return "Playing previous track...";
    }
}
