package refactoring_guru.patterns.iterator.example.social_networks;

import java.util.HashMap;
import java.util.Map;

public class Facebook implements SocialNetwork {
    public Map<String, Profile> database = new HashMap<>();

    @Override
    public ProfileIterator getFriendsIterator(String email) {
        return new FacebookIterator(this, email);
    }

    @Override
    public ProfileIterator getCoworkerIterator(String email) {
        return getFriendsIterator(email);
    }

    @Override
    public void add(Profile profile) {
        database.put(profile.getEmail(), profile);
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