package refactoring_guru.patterns.chain_of_responsibilyty.media_request;

import java.util.ArrayList;
import java.util.List;

public class MobileGameRequest extends Request {
    private static List<String> games = new ArrayList<>();

    static {
        games.add("sudoku.apk");
        games.add("solitaire.jar");
        games.add("pinball.sisx");
    }

    @Override
    public void setNext(Request next) {
        nextInChain = next;
    }

    @Override
    public String process(String request) {
        String result;
        String format = request.substring(request.indexOf('.'), request.length());
        if (format.equals(".jar") || format.equals(".apk") || format.equals(".sisx")) {
            for (String mobileGame : games) {
                if (request.equals(mobileGame)) {
                    return "Found file: " + mobileGame;
                }
            }
            return "File: " + request + ", doesn't exist";
        } else {
            result = nextInChain.process(request);
        }
        return result;
    }
}
