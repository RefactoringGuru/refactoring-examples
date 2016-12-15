package refactoring_guru.patterns.iterator.example.social_networks;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String name;
    private String email;
    private List<Profile> persons = new ArrayList<>();

    public Profile(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public void addToSocialNetwork(Profile person) {
        persons.add(person);
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public List getProfiles() {
        return this.persons;
    }
}