package refactoring_guru.patterns.proxy.example;

import refactoring_guru.patterns.proxy.example.media_framework_lib.CashedYoutubeClass;

public class Application {
    public static void main(String[] args) {
        CashedYoutubeClass youtubeLib = new CashedYoutubeClass();
        YoutubeManager manager = new YoutubeManager(youtubeLib, 1);
        manager.reactOnUserInput();
    }
}
