package refactoring_guru.patterns.iterator.example.social_networks;


import refactoring_guru.patterns.iterator.example.social_networks.Profile;

public interface ProfileIterator {
    public boolean hasNext();
    public Profile getNext();
}