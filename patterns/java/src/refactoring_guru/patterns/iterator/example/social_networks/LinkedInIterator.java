package refactoring_guru.patterns.iterator.example.social_networks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinkedInIterator implements ProfileIterator {
    private SocialNetwork network;
    private String email;
    private int currentPosition;
    private Map<Integer, Profile> cache = new HashMap<>();

    public LinkedInIterator(SocialNetwork network, String email) {
        this.network = network;
        this.email = email;
    }

    private void lazyLoad() {
        if (cache.size() == 0) {
            List<Profile> profiles = network.getProfile(this.email).getCoworkers();
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
