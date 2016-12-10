package refactoring_guru.patterns.iterator.example;

import refactoring_guru.patterns.iterator.example.social_networks.Facebook;
import refactoring_guru.patterns.iterator.example.social_networks.LinkedIn;
import refactoring_guru.patterns.iterator.example.social_networks.SocialNetwork;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Demo {
    public static void main(String[] args) {
        SocialNetwork network = null;
        if (args[0].equals("-fb")) {
            network = new Facebook();
        }
        if (args[0].equals("-li")) {
            network = new LinkedIn();
        }
        if (network == null) {
            throw new NotImplementedException();
        }
        Application application = new Application(network);
        application.sendSpamToFriends("Hello World!", "p.mcmartney@gmail.com");
    }
}