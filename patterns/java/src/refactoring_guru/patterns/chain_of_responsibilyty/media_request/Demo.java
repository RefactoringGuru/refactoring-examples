package refactoring_guru.patterns.chain_of_responsibilyty.media_request;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        // Формирование цепочки обязаностей
        Request request = new VideoRequest();
        request.setNext(new AudioRequest());
        request.nextInChain.setNext(new BookRequest());
        request.nextInChain.nextInChain.setNext(new MobileGameRequest());

        System.out.println("Enter the query:");
        String fileSearch = reader.readLine();
        // Поиск медиафайлов на импровизированых серверах
        String result = request.process(fileSearch);
        System.out.println(result);
    }
}
