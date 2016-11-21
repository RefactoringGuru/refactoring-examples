package refactoring_guru.patterns.proxy.example.media_framework_lib;

import java.util.ArrayList;
import java.util.List;

public class CashedYoutubeClass implements ThirdPartyYoutubeLib {
    private ThirdPartyYoutubeLib youtubeService = new ThirdPartyYoutubeClass();
    private List<String> listCache = new ArrayList<>();
    private String videoCache;
    private boolean needReset = true;
    private boolean downloadExist = false;
    private long time;

    public CashedYoutubeClass() {
        this.youtubeService = new ThirdPartyYoutubeClass();
        this.time = System.currentTimeMillis();
        this.listCache = this.youtubeService.listVideos();
    }

    @Override
    public List<String> listVideos() {
        checkReset();
        if (listCache.isEmpty() && !needReset) {
            listCache = youtubeService.listVideos();
        }
        return listCache;
    }

    @Override
    public String getVideoInfo(int id) {
        checkReset();
        if (!listCache.isEmpty() && !needReset) {
            videoCache = youtubeService.getVideoInfo(id);
        }
        return videoCache;
    }

    @Override
    public void downloadVideo(int id) {
        checkDownloadExist();
        if (!downloadExist && needReset) {
            listCache = youtubeService.listVideos();
            System.out.println("File '" + listCache.get(id - 1) + "' - was downloaded");
        }
    }

    public void checkReset() {
        long currentTime = System.currentTimeMillis();
        long different = currentTime - this.time;
        if (different < 300_000) { // 5 min
            this.needReset = false;
        } else {
            this.needReset = true;
        }
    }

    public void checkDownloadExist() {
        if (listCache.isEmpty() || listCache == null) {
            this.downloadExist = true;
        } else {
            this.downloadExist = false;
        }
    }
}
