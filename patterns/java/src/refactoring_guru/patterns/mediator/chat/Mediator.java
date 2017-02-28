package refactoring_guru.patterns.mediator.chat;

public interface Mediator {
    void sendMessage(String msg, Client client);
    void addClient(Client client);
}
