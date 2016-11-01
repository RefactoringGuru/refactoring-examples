package refactoring_guru.patterns.bridge.example.Devices;

public interface Device {
    public boolean isEnable();
    public void enable();
    public void disable();
    public int getVolume();
    public void setVolume(int percent);
    public int getChannel();
    public void setChannel(int channel);
}
