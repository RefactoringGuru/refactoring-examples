package refactoring_guru.patterns.chain_of_responsibilyty.media_request;

import java.util.ArrayList;
import java.util.List;

public class AudioRequest extends Request {
    private static List<String> audios = new ArrayList<>();

    static  {
        audios.add("Track1.mp3");
        audios.add("Track2.mp3");
        audios.add("Track3.mp3");
    }

    @Override
    public void setNext(Request next) {
        nextInChain = next;
    }

    @Override
    public String process(String request) {
        String result;
        String format = request.substring(request.indexOf('.'), request.length());
        if (format.equals(".mp3") || format.equals(".flac") || format.equals(".wav")) {
            for (String audio : audios) {
                if (request.equals(audio)) {
                    return "Found file: " + audio;
                }
            }
            return "File: " + request + ", doesn't exist";
        } else {
            result = nextInChain.process(request);
        }
        return result;
    }
}
