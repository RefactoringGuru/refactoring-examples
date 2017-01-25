package refactoring_guru.patterns.bridge.example;

import refactoring_guru.patterns.bridge.example.devices.Device;
import refactoring_guru.patterns.bridge.example.devices.Radio;
import refactoring_guru.patterns.bridge.example.devices.Tv;
import refactoring_guru.patterns.bridge.example.remotes.AdvancedRemote;
import refactoring_guru.patterns.bridge.example.remotes.BasicRemote;

public class Demo {
    public static void main(String[] args) {
        testDevice(new Tv());
        testDevice(new Radio());
    }

    public static void testDevice(Device device) {
        System.out.println("Tests with basic remote.");
        BasicRemote basicRemote = new BasicRemote(device);
        basicRemote.power();
        device.printStatus();

        System.out.println("Tests with advanced remote.");
        AdvancedRemote advancedRemote = new AdvancedRemote(device);
        advancedRemote.power();
        advancedRemote.mute();
        device.printStatus();
    }
}
