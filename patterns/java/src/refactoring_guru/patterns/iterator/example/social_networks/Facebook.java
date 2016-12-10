package refactoring_guru.patterns.iterator.example.social_networks;

import java.util.HashMap;
import java.util.Map;

public class Facebook implements SocialNetwork {
    public static Map<String, Profile> database = new HashMap<>();

    static {
        database.put("j.lennon@gmail.com", new Profile("j.lennon@gmail.com", "John Lennon", "friends"));
        database.put("p.mcmartney@gmail.com", new Profile("p.mcmartney@gmail.com", "Paul McCartney", "friends"));
        database.put("g.harrison@gmail.com", new Profile("g.harrison@gmail.com", "George Harrison", "friends"));
        database.put("r.starr@gmail.com", new Profile("r.starr@gmail.com", "Ringo Starr", "friends"));
    }

    @Override
    public ProfileIterator getFriendsIterator(String email) {
        return new FacebookIterator(this, email);
    }

    @Override
    public ProfileIterator getCoworkerIterator(String email) {
        return getFriendsIterator(email);
    }

    public Profile getProfile(String email) {
        // Emulates long network connection, which you would expect in the real life.
        try {
            Thread.currentThread().sleep(2500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return database.get(email);
    }
}