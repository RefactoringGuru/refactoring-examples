package refactoring_guru.patterns.mediator.client_server_mediator;

/**
 * EN: Common mediator interface.
 *
 * RU: Общий интерфейс посредников.
 */
public interface Mediator {
    void sendResponse(String content);
    void makeRequest();
    String queryDb();
}
