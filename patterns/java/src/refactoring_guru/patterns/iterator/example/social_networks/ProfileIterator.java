package refactoring_guru.patterns.iterator.example.social_networks;

public interface ProfileIterator {
    public boolean hasNext();
    public Profile getNext();
}