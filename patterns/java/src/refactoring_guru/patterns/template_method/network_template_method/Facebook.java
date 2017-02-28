package refactoring_guru.patterns.template_method.network_template_method;

/**
 * EN: Class of social network
 *
 * RU: Класс социальной сети.
 */
public class Facebook extends Network {
    public boolean authenticate() {
        return true;
    }

    public boolean sendData(byte[] data) {
        boolean messagePosted = true;
        if (messagePosted) {
            System.out.println("Message: " + new String(data) + "\n" + "was posted on Facebook");
            return true;
        } else {
            return false;
        }
    }
}
