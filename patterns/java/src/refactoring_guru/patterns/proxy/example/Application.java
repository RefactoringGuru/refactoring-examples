package refactoring_guru.patterns.proxy.example;

import refactoring_guru.patterns.proxy.example.media_framework_lib.CachedYoutubeClass;

public class Application {
    public static void main(String[] args) {
        CachedYoutubeClass youtubeLib = new CachedYoutubeClass();
        YoutubeManager manager = new YoutubeManager(youtubeLib, 1);
        manager.reactOnUserInput();
    }
}
