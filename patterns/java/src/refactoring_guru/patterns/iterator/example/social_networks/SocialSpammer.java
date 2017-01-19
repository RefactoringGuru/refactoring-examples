package refactoring_guru.patterns.iterator.example.social_networks;

public class SocialSpammer {
    Profile profile;
    public void send(ProfileIterator iterator, String message) {
        while (iterator.hasNext()) {
            profile = iterator.getNext();
            sendSingle(profile.getEmail(), message);
        }
    }

    public void sendSingle(String email, String message) {
        System.out.println("To '" + email + "' was sent message '" + message + "'");
    }
}
