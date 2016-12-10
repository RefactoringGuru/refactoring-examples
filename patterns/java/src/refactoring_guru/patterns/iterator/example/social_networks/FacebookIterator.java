package refactoring_guru.patterns.iterator.example.social_networks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FacebookIterator implements ProfileIterator {
    private Facebook facebook;
    public String email;
    private int currentPosition;
    private Map<Integer, Profile> cache = new HashMap<>();

    public FacebookIterator(Facebook facebook, String email) {
        this.facebook = facebook;
        this.email = email;
    }

    private void lazyLoad() {
        if (cache.size() == 0) {
            int i = 1;
            Set set = facebook.database.keySet();
            for (Object key : set) {
                cache.put(i, facebook.getProfile((String)key));
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
