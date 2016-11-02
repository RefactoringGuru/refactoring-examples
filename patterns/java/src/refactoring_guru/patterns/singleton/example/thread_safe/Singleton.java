package refactoring_guru.patterns.singleton.example.thread_safe;

public final class Singleton {
    private static Singleton instance;
    public static String name;

    private Singleton(String name) {
        this.name = name;
    }

    public static synchronized Singleton getInstance(String name) {
        if (instance == null) {
            instance = new Singleton(name);
        }
        return instance;
    }
}
