package refactoring_guru.patterns.singletone.example;

public class Application {
    public static void main(String[] args) {
        Singleton foo = Singleton.getInstance();
        Singleton bar = Singleton.getInstance();
        foo.equals(bar); // true
    }
}
