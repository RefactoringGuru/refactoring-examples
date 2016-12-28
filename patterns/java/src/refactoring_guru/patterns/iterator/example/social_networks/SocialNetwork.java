package refactoring_guru.patterns.iterator.example.social_networks;

import java.util.List;

public interface SocialNetwork {
    public ProfileIterator getIterator(String email);
    public void add(Profile profile);
    public Profile getProfile(String email);
}
