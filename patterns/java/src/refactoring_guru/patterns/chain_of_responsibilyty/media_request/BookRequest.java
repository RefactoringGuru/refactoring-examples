package refactoring_guru.patterns.chain_of_responsibilyty.media_request;

import java.util.ArrayList;
import java.util.List;

public class BookRequest extends Request {
    private static List<String> books = new ArrayList<>();

    static {
        books.add("Treasure Island.epub");
        books.add("1984.mobi");
        books.add("Hobbit.fb2");
    }

    @Override
    public void setNext(Request next) {
        nextInChain = next;
    }

    @Override
    public String process(String request) {
        String result;
        String format = request.substring(request.indexOf('.'), request.length());
        if (format.equals(".epub") || format.equals(".mobi") || format.equals(".fb2")) {
            for (String book : books) {
                if (request.equals(book)) {
                    return "Found file: " + book;
                }
            }
            return "File: " + request + ", doesn't exist";
        } else {
            result = nextInChain.process(request);
        }
        return result;
    }
}
