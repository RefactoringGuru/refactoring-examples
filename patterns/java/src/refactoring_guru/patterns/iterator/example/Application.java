package refactoring_guru.patterns.iterator.example;

import refactoring_guru.patterns.iterator.example.social_networks.*;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public SocialNetwork network;
    public SocialSpammer spammer;
    public ProfileIterator iterator;

    public Application(SocialNetwork network) {
        this.spammer = new SocialSpammer();
        this.network = network;
    }

    public void sendSpam(String message, String email) {
        iterator = network.getIterator(email);
        spammer.send(iterator, message);
    }
}