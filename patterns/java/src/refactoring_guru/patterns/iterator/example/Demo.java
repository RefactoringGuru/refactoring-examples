package refactoring_guru.patterns.iterator.example;

public class Demo {
    public static void main(String[] args) {
        Application application = new Application();
        application.config();
        if (application.network.getClass() == Facebook.class) {
            application.sendSpamToFriends("Hello World", 2);
        } else {
            application.sendSpamToCoworkers("Hello World", 2);
        }
    }
}
