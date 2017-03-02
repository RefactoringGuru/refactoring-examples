package refactoring_guru.patterns.mediator.client_server_mediator;

public class DataBase {
    protected Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public String getData() {
        return "World!";
    }
}
