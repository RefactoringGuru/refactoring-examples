package refactoring_guru.patterns.iterator.example;

import refactoring_guru.patterns.iterator.example.social_networks.*;

public class Application {
    public SocialNetwork network;
    public SocialSpammer spammer;
    public ProfileIterator iterator;

    public Application(SocialNetwork network) {
        this.spammer = new SocialSpammer();
        this.network = network;
    }

    public void sendSpamToFriends(String message, String email) {
        iterator = network.getFriendsIterator(email);
        spammer.send(iterator, message);
    }

    public void sendSpamToCoworkers(String message, String email) {
        iterator = network.getCoworkerIterator(email);
        spammer.send(iterator, message);
    }
}