package refactoring_guru.patterns.mediator.client_server_mediator;

public class MediatorDemo {
    public static void main(String[] args) {
        Client client = new Client();

        // EN: Create a mediator object in memory to establish links.
        //
        // RU: Создаем в памяти объект посредника, что бы установить связи.
        new Dialog(new Server(), new DataBase(), client);

        // EN: Client does not know anything about the server and databases,
        // but it gets all the necessary information from the mediator
        //
        // RU: Клиент ничего не знает о сервере и базах данных, но он получает
        // всю нужную информацию от посредника
        client.request();
    }
}
