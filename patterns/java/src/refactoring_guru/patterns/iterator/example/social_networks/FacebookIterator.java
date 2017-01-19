package refactoring_guru.patterns.iterator.example.social_networks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacebookIterator implements ProfileIterator {
    private Facebook facebook;
    private String email;
    private int currentPosition;
    private Map<Integer, Profile> cache = new HashMap<>();

    public FacebookIterator(Facebook facebook, String email) {
        this.facebook = facebook;
        this.email = email;
    }

    private void lazyLoad() {
        if (cache.size() == 0) {
            List<Profile> profiles = facebook.getProfile(this.email).getFriends();
            int index = 1;
            for (Profile profile : profiles) {
                cache.put(index, profile);
                index++;
            }
        }
    }

    @Override
    public boolean hasNext() {
        lazyLoad();
        return cache.size() > currentPosition;
    }

    @Override
    public Profile getNext() {
        if (hasNext()) {
            currentPosition++;
            return cache.get(currentPosition);
        }
        return null;
    }
}
