package refactoring_guru.patterns.iterator.example;

import java.util.Random;

public class Application {
    public SocialNetwork network;
    public SocialSpammer spammer;
    public ProfileIterator iterator;

    public void config() {
        Random random = new Random();
        int i = random.nextInt(1);
        if (i == 1) {
            this.network = new Facebook();
        }
        if (i == 0) {
            this.network = new LinkedIn();
        }
        this.spammer = new SocialSpammer();
    }

    public void sendSpamToFriends(String message, int profileId) {
        iterator = network.getFriendsIterator(profileId);
        spammer.send(iterator, message);
    }

    public void sendSpamToCoworkers(String message, int profileId) {
        iterator = network.getCoworkerIterator(profileId);
        spammer.send(iterator, message);
    }
}
