package refactoring_guru.patterns.iterator.example;

import refactoring_guru.patterns.iterator.example.social_networks.Facebook;
import refactoring_guru.patterns.iterator.example.social_networks.LinkedIn;
import refactoring_guru.patterns.iterator.example.social_networks.Profile;
import refactoring_guru.patterns.iterator.example.social_networks.SocialNetwork;

public class Demo {
    public static SocialNetwork network = null;

    public static void main(String[] args) {
        if (args[0].equals("-fb")) {
            network = new Facebook();
        }
        if (args[0].equals("-li")) {
            network = new LinkedIn();
        }
        if (network == null) {
            throw new IllegalArgumentException("Wrong argument");
        }
        initProfiles();
        Application application = new Application(network);
        application.sendSpamToFriends("Hello, how are You?", "ann@bing.com");
        System.out.println("----------------------------------");
        application.sendSpamToCoworkers("Hi, what are You doing?", "ann@bing.com");
    }

    public static void initProfiles() {
        Profile anna = new Profile("ann@bing.com", "Anna");
        Profile max = new Profile("mad_max@ya.com", "Maximilian");
        Profile bill = new Profile("bill@microsoft.eu", "Billie");
        Profile john = new Profile("avanger@ukr.net", "John Day");
        Profile sam = new Profile("sam@amazon.com", "Sam Kitting");
        Profile liza = new Profile("catwoman@yahoo.com", "Liza");

        network.add(anna);
        network.add(john);
        network.add(liza);

        anna.addToCoworkers(max);
        anna.addToCoworkers(bill);
        anna.addToFriends(john);
        anna.addToFriends(sam);
        anna.addToFriends(liza);

        john.addToFriends(max);
        john.addToFriends(sam);
        john.addToFriends(bill);

        liza.addToCoworkers(anna);
        liza.addToCoworkers(bill);
        liza.addToCoworkers(john);
    }
}