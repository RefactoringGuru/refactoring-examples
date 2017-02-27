package refactoring_guru.patterns.chain_of_responsibilyty.user_validator;

public class Demo {
    public static void main(String[] args) {
        Server server = new Server();
        server.register();
        server.logIn();
    }
}
