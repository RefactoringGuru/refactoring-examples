package refactoring_guru.patterns.singleton.example.non_thread_safe;

public class DemoSingleThread {
    public static void main(String[] args) {
        System.out.println("RESULTS" + "\n" +
                           "Single value means singleton was created just once." + "\n" +
                           "Multiple values mean several singleton objects were created." + "\n");
        Singleton singleton = Singleton.getInstance("FOO");
        Singleton anotherSingleton = Singleton.getInstance("BAR");
        System.out.println(singleton.value);
        System.out.println(anotherSingleton.value);
    }
}
