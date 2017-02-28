package refactoring_guru.patterns.mediator.chat;

public class Client {
    private Mediator mediator;
    private String name;

    public Client(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public void send(String msg){
        System.out.println(name + ": SENT message: " + msg);
        mediator.sendMessage(msg, this);
    }

    public void receive(String msg) {
        System.out.println(name + ": Received message: " + msg);
    }
}
