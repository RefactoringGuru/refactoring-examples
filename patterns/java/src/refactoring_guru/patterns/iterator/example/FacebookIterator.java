package refactoring_guru.patterns.iterator.example;

public class FacebookIterator implements ProfileIterator {
    private Facebook facebook;
    private int profileId;
    private String type;
    private int currentPosition;
    private Profile[] cache;

    public FacebookIterator(Facebook facebook, int profileId, String type) {
        this.facebook = facebook;
        this.profileId = profileId;
        this.type = type;
    }

    private void initIfNeeded() {
        if (cache == null) {
            cache = facebook.sendSophisticatedSocialGraphRequest(profileId);
        }
    }

    @Override
    public boolean hasNext() {
        initIfNeeded();
        return cache.length < currentPosition;
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
