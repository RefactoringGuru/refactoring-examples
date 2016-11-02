package refactoring_guru.patterns.singleton.example.thread_safe;

public class ThreadDemo implements Runnable {

    @Override
    public void run() {
        Client client = new Client();
        client.singleton = Singleton.getInstance("BAR");
        System.out.println(client.singleton.name);
    }
}
