package refactoring_guru.patterns.singletone.example;

public class MultiplySingleton {
    private static MultiplySingleton instance = new MultiplySingleton();;

    private MultiplySingleton() {}

    public static MultiplySingleton getInstance() {
        return instance;
    }
}
