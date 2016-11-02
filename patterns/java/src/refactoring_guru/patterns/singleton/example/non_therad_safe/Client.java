package refactoring_guru.patterns.singleton.example.non_therad_safe;

public class Client {
    public Singleton singleton;

    public void setNameSingleton(String name) {
        singleton = Singleton.getInstance(name);
    }
}
