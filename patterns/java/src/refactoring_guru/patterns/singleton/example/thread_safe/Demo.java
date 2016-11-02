package refactoring_guru.patterns.singleton.example.thread_safe;

public class Demo {
    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadDemo());
        thread.start();

        Client client = new Client();
        client.setNameSingleton("FOO");
        System.out.println(client.singleton.name);
    }
}

// results
//
// FOO
// FOO

