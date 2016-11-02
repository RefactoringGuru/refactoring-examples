package refactoring_guru.patterns.singleton.example.non_therad_safe;

public class Demo {
    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadDemo());
        thread.start();

        Client client = new Client();
        client.setNameSingleton("BAR");
        System.out.println(client.singleton.name);
    }
}

// results
//
// FOO
// BAR
//
// FOO
// FOO
//
// BAR
// BAR
