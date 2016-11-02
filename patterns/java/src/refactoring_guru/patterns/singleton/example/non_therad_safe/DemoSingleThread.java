package refactoring_guru.patterns.singleton.example.non_therad_safe;

public class DemoSingleThread {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance("FOO");
        Client client = new Client();
        System.out.println(singleton.name); // FOO
        client.setNameSingleton("BAR");
        System.out.println(singleton.name); // FOO
    }
}
