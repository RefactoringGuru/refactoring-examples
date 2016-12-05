package refactoring_guru.patterns.iterator.example;

public interface SocialNetwork {
    public ProfileIterator getFriendsIterator(int profileId);
    public ProfileIterator getCoworkerIterator(int profileId);
}
