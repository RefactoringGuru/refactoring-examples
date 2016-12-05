package refactoring_guru.patterns.iterator.example;

public class Facebook implements SocialNetwork {
    public Profile[] base = {
            new Profile("John", "Lennon", "j.lennon@gmail.com", "friends"),
            new Profile("Paul", "McCartney", "p.mcmartney@gmail.com", "friends"),
            new Profile("George", "Harrison", "g.harrison@gmail.com", "friends"),
            new Profile("Ringo", "Starr", "r.starr@gmail.com", "friends"),
    };

    @Override
    public ProfileIterator getFriendsIterator(int profileId) {
        return new FacebookIterator(this, profileId, "friends");
    }

    @Override
    public ProfileIterator getCoworkerIterator(int profileId) {
        return getFriendsIterator(profileId);
    }

    public Profile[] sendSophisticatedSocialGraphRequest(int profileId) {
        Profile[] cache = new Profile[profileId + 1];
        for (int i = 0; i <= profileId; i++) {
            cache[i] = base[i];
        }
        return cache;
    }
}
