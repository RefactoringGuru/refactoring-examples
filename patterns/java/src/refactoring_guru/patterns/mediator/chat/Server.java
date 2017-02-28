package refactoring_guru.patterns.mediator.chat;

import java.util.*;

public class Server implements Mediator {
    private List<Client> clients = new ArrayList<>();

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void sendMessage(String message, Client client) {
        for (Client c : clients) {
            if (c != client) {
                c.receive(message);
            }
        }
    }
}
