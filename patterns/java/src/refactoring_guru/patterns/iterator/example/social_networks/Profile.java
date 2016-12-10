package refactoring_guru.patterns.iterator.example.social_networks;

public class Profile {
    public String name;
    public String email;
    public String type;

    public Profile(String email, String name, String type) {
        this.email = email;
        this.name = name;
        this.type = type;
    }
}