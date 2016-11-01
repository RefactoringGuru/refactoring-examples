package refactoring_guru.patterns.bridge.example.Devices;

public class Tv implements Device {
    private boolean on = false;
    private int volume = 30;
    private int channel = 1;

    @Override
    public boolean isEnable() {
        if (on) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void enable() {
        on = true;
    }

    @Override
    public  void disable() {
        on = false;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void setVolume(int percent) {
        if (volume >= 100) {
            return;
        }
        this.volume += percent;
    }

    @Override
    public int getChannel() {
        return channel;
    }

    @Override
    public void setChannel(int channel) {
        this.channel += channel;
    }
}
