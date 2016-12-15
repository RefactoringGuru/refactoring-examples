package refactoring_guru.patterns.iterator.example;

import refactoring_guru.patterns.iterator.example.social_networks.*;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public SocialNetwork network;
    public SocialSpammer spammer;
    public ProfileIterator iterator;
    public List<Profile> profiles = new ArrayList<>();

    public Application(SocialNetwork network) {
        this.spammer = new SocialSpammer();
        this.network = network;
        initProfilesForTestingSystem();
    }

    public void sendSpamToFriends(String message, String email) {
        iterator = network.getFriendsIterator(email);
        spammer.send(iterator, message);
    }

    public void sendSpamToCoworkers(String message, String email) {
        iterator = network.getCoworkerIterator(email);
        spammer.send(iterator, message);
    }

    public void initProfilesForTestingSystem() {
        profiles.add(new Profile("j.lennon@gmail.com", "John Lennon"));
        profiles.add(new Profile("p.mcmartney@gmail.com", "Paul McCartney"));
        profiles.add(new Profile("g.harrison@gmail.com", "George Harrison"));
        profiles.add(new Profile("r.starr@gmail.com", "Ringo Starr"));
        for (Profile profile : profiles) {
            network.add(profile);
        }
        for (Profile profile : profiles) {
            internalFillingProfiles(profile);
        }
    }

    public void internalFillingProfiles(Profile profile) {
        for (Profile internalProfile : profiles) {
            if (profile.getEmail().equals(internalProfile.getEmail())) {
                continue;
            }
            profile.addToSocialNetwork(internalProfile);
        }
    }
}