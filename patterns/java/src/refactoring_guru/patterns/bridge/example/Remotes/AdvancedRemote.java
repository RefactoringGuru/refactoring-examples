package refactoring_guru.patterns.bridge.example.Remotes;

import refactoring_guru.patterns.bridge.example.Devices.Device;

public class AdvancedRemote extends BasicRemote {

    public AdvancedRemote(Device device) {
        super.device = device;
    }

    public void mute() {
        device.setVolume(0);
    }
}
