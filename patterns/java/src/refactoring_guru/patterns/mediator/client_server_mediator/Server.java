package refactoring_guru.patterns.mediator.client_server_mediator;

public class Server {
    protected Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void process() {
        String data = mediator.queryDb();
        mediator.sendResponse("Hello " + data);
    }
}
