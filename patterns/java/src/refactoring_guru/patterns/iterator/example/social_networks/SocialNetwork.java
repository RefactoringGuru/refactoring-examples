package refactoring_guru.patterns.iterator.example.social_networks;

import refactoring_guru.patterns.iterator.example.iterators.ProfileIterator;

public interface SocialNetwork {
    public ProfileIterator getFriendsIterator(String profileEmail);

    public ProfileIterator getCoworkersIterator(String profileEmail);
}
