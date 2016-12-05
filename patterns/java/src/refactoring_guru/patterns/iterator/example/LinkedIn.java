package refactoring_guru.patterns.iterator.example;

public class LinkedIn implements SocialNetwork {
    public Profile[] base = {
            new Profile("John", "Lennon", "j.lennon@gmail.com", "coworkers"),
            new Profile("Paul", "McCartney", "p.mcmartney@gmail.com", "coworkers"),
            new Profile("George", "Harrison", "g.harrison@gmail.com", "coworkers"),
            new Profile("Ringo", "Starr", "r.starr@gmail.com", "coworkers"),
    };

    @Override
    public ProfileIterator getFriendsIterator(int profileId) {
        return getCoworkerIterator(profileId);
    }

    @Override
    public ProfileIterator getCoworkerIterator(int profileId) {
        return new LinkedInIterator(this, profileId, "coworkers");
    }

    public Profile[] sendSophisticatedSocialGraphRequest(int profileId) {
        Profile[] cache = new Profile[profileId + 1];
        for (int i = 0; i <= profileId; i++) {
            cache[i] = base[i];
        }
        return cache;
    }
}
