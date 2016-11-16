package refactoring_guru.patterns.proxy.example.media_framework_lib;

import java.util.ArrayList;
import java.util.List;

public class ThirdPartyYoutubeClass implements ThirdPartyYoutubeLib {
    private List<String> list = new ArrayList<>();

    @Override
    public List<String> listVideos() {
        System.out.println("Connecting to http://www.youtube.com");
        delay();
        System.out.println("\n" + "Connected" + "\n");
        System.out.println("Creating video list");
        delay();
        System.out.println("\n" + "Video list was created" + "\n");
        String[] array = new String[] {"Forsage4.avi", "Dog play with ball.mp4",
                "Dancing video.mpq", "Barselona vs RealM.mov",
                "Programing lesson#1.avi", "Tayson vs Levis.mpq" };
        for (String s : array) {
            list.add(s);
        }
        return list;
    }

    public void delay() {
        for (int i = 0; i < 37; i++) {
            try {
                Thread.sleep(100);
                System.out.print("*");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getVideoInfo(int id) {
        String result = list.get(id - 1);
        String info = "File name: " +result.substring(0, result.indexOf(".")) + "\n"
                + "Video format: " + result.substring(result.indexOf(".") + 1, result.length());
        return info;
    }

    @Override
    public void downloadVideo(int id) {
        System.out.println("File '" + list.get(id - 1) + "' - was downloaded");
    }
}
