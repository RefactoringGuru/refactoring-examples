package refactoring_guru.patterns.bridge.example;

import refactoring_guru.patterns.bridge.example.Devices.Radio;
import refactoring_guru.patterns.bridge.example.Devices.Tv;
import refactoring_guru.patterns.bridge.example.Remotes.AdvancedRemote;
import refactoring_guru.patterns.bridge.example.Remotes.BasicRemote;
import refactoring_guru.patterns.bridge.example.Remotes.Remote;

public class Demo {
    public static void main(String[] args) {
        Tv tv = new Tv();
        Remote tvRemote = new BasicRemote(tv);
        tvRemote.power();
        if (tv.isEnable()) {
            System.out.println("TV was enabled");
        }

        Radio radio = new Radio();
        Remote radioRemote = new AdvancedRemote(radio);
        radioRemote.power();
    }
}
