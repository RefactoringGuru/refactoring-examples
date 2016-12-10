package refactoring_guru.patterns.iterator.example.social_networks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LinkedInIterator implements ProfileIterator {
    private LinkedIn linkedIn;
    public String email;
    private int currentPosition;
    private Map<Integer, Profile> cache = new HashMap<>();

    public LinkedInIterator(LinkedIn linkedIn, String email) {
        this.linkedIn = linkedIn;
        this.email = email;
    }

    private void lazyLoad() {
        if (cache.size() == 0) {
            int i = 1;
            Set set = linkedIn.database.keySet();
            for (Object key : set) {
                cache.put(i, linkedIn.getProfile((String)key));
                i++;
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
