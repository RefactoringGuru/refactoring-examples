package refactoring_guru.patterns.chain_of_responsibilyty.media_request;

import java.util.ArrayList;
import java.util.List;

public class VideoRequest extends Request {
    private static List<String> videos = new ArrayList<>();

    static {
        videos.add("Star Wars: Episode one.avi");
        videos.add("Matrix 2. Reload.mkv");
        videos.add("Home Alone.avi");
    }

    @Override
    public void setNext(Request next) {
        nextInChain = next;
    }

    @Override
    public String process(String request) {
        String result;
        String format = request.substring(request.indexOf('.'), request.length());
        if (format.equals(".avi") || format.equals(".mkv") || format.equals(".mpg")) {
            for (String video : videos) {
                if (request.equals(video)) {
                    return "Found file: " + video;
                }
            }
            result = "File: " + request + ", doesn't exist";
        } else {
            result = nextInChain.process(request);
        }
        return result;
    }
}
