package refactoring_guru.patterns.mediator.client_server_mediator;

/**
 * EN: Concrete mediator that links all the components together in one
 * application, it receives signals from all components and knows how
 * to process them.
 *
 * RU: Посредник который связывает все компоненты приложения между собой,
 * он получает сигналы от всех компонентов и знает как их обрабатывать.
 */
public class Dialog implements Mediator {
    private Server server;
    private DataBase dataBase;
    private Client client;

    public Dialog(Server server, DataBase dataBase, Client client) {
        this.server = server;
        this.dataBase = dataBase;
        this.client = client;

        this.server.setMediator(this);
        this.dataBase.setMediator(this);
        this.client.setMediator(this);
    }

    @Override
    public void makeRequest() {
        server.process();
    }

    public String queryDb() {
        return dataBase.getData();
    }

    @Override
    public void sendResponse(String content) {
        client.output(content);
    }
}
