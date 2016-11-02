package refactoring_guru.patterns.singleton.example.non_therad_safe;

public final class Singleton {
    private static Singleton instance;
    public String name;

    private Singleton(String name) {
        this.name = name;
    }

    public static Singleton getInstance(String name) {
        if (instance == null) {
            instance = new Singleton(name);
        }
        return instance;
    }
}
