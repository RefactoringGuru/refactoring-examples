package refactoring_guru.patterns.iterator.example.iterators;

import refactoring_guru.patterns.iterator.example.profile.Profile;

public interface ProfileIterator {
    public boolean hasNext();

    public Profile getNext();

    public void reset();
}