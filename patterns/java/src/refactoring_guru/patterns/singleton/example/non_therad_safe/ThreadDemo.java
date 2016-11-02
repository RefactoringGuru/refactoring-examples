package refactoring_guru.patterns.singleton.example.non_therad_safe;

public class ThreadDemo implements Runnable {

    @Override
    public void run() {
        Client client = new Client();
        client.singleton = Singleton.getInstance("FOO");
        System.out.println(client.singleton.name);
    }
}
