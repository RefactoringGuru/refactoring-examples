package refactoring_guru.patterns.iterator.example;

public class LinkedInIterator implements ProfileIterator {
    private LinkedIn linkedIn;
    private int profileId;
    private String type;
    private int currentPosition;
    private Profile[] cache;

    public LinkedInIterator(LinkedIn linkedIn, int profileId, String type) {
        this.linkedIn = linkedIn;
        this.profileId = profileId;
        this.type = type;
    }

    private void initIfNeeded() {
        if (cache == null) {
            cache = linkedIn.sendSophisticatedSocialGraphRequest(profileId);
        }
    }

    @Override
    public boolean hasNext() {
        initIfNeeded();
        return cache.length > currentPosition;
    }

    @Override
    public Profile getNext() {
        if (hasNext()) {
            currentPosition++;
            return cache[currentPosition];
        }
        return null;
    }
}
