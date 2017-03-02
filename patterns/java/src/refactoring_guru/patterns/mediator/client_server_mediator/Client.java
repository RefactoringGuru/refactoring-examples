package refactoring_guru.patterns.mediator.client_server_mediator;

/**
 * EN: Client application that make get-request and receives a response in
 * the form like index.html.
 *
 * RU: Клиентское приложение, которое отдает get-запросы и получет на них ответ
 * в виде типа index.html.
 */
public class Client {
    protected Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void request() {
        mediator.makeRequest();
    }

    public void output(String content) {
        System.out.println(content);
    }
}
