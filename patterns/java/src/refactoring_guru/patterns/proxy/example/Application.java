package refactoring_guru.patterns.proxy.example;

import refactoring_guru.patterns.proxy.example.media_framework_lib.CaсhedYoutubeClass;

public class Application {
    public static void main(String[] args) {
        CaсhedYoutubeClass youtubeLib = new CaсhedYoutubeClass();
        YoutubeManager manager = new YoutubeManager(youtubeLib, 1);
        manager.reactOnUserInput();
    }
}
