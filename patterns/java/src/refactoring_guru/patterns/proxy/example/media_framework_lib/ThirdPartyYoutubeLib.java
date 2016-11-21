package refactoring_guru.patterns.proxy.example.media_framework_lib;

import java.util.List;

public interface ThirdPartyYoutubeLib {
    public List<String> listVideos();
    public String getVideoInfo(int id);
    public void downloadVideo(int id);
}
