package refactoring_guru.patterns.iterator.example.social_networks;

public interface SocialNetwork {
    public ProfileIterator getFriendsIterator(String email);
    public ProfileIterator getCoworkerIterator(String email);
    public void add(Profile profile);
}
