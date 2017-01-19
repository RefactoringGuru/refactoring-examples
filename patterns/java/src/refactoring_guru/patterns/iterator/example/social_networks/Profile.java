package refactoring_guru.patterns.iterator.example.social_networks;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String name;
    private String email;
    private List<Profile> friends = new ArrayList<>();
    private List<Profile> coworkers = new ArrayList<>();

    public Profile(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public void addToFriends(Profile friend) {
        friends.add(friend);
    }

    public void addToCoworkers(Profile coworker) {
        coworkers.add(coworker);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public List<Profile> getFriends() {
        return this.friends;
    }

    public List<Profile> getCoworkers() {
        return coworkers;
    }
}