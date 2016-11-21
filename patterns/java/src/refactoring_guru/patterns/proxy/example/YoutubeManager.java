package refactoring_guru.patterns.proxy.example;

import refactoring_guru.patterns.proxy.example.media_framework_lib.CashedYoutubeClass;
import refactoring_guru.patterns.proxy.example.media_framework_lib.ThirdPartyYoutubeLib;

import java.util.List;

public class YoutubeManager {
    private ThirdPartyYoutubeLib service;
    private int n;

    public YoutubeManager(CashedYoutubeClass service, int n) {
        this.service = service;
        this.n = n;
    }

    public void renderVideoPage() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        String info = service.getVideoInfo(this.n);
        System.out.println("Render video page");
        System.out.println(info);
    }

    public void renderListPanel() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        List<String> list = service.listVideos();
        System.out.println("\n" + "Render the list of video thumbnails");
        int n = 1;
        for (String s : list) {
            System.out.println(n + ". " + s);
            n++;
        }
    }

    public void reactOnUserInput() {
        renderVideoPage();
        renderListPanel();
    }
}
