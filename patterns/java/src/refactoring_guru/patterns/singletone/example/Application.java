package refactoring_guru.patterns.singletone.example;

public class Application {
    public static void main(String[] args) {
        ThreadSafeSingleton foo = ThreadSafeSingleton.getInstance().getInstance();
        ThreadSafeSingleton bar = ThreadSafeSingleton.getInstance().getInstance();
        foo.equals(bar); // true
    }
}
