package refactoring_guru.patterns.mediator.chat;

public class ChatClient {
    public static void main(String[] args) {
        Mediator mediator = new Server();

        Client admin = new Client(mediator, "ADMIN");
        Client ann = new Client(mediator, "Ann");
        Client jim = new Client(mediator, "Jim");
        mediator.addClient(admin);
        mediator.addClient(ann);
        mediator.addClient(jim);

        admin.send("Welcome to the chat!");
        ann.send("Hi!");
    }
}
