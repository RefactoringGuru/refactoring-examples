package refactoring_guru.patterns.chain_of_responsibilyty.user_authenticator;

import java.util.HashMap;
import java.util.Map;

/**
 * RU: Сервер ничего не опрашивает у клианта и работает с поданными в него данными.
 */
public class Server {
    private static Map<String, String> users = new HashMap<>();
    private Middleware middleware;

    public void register(String email, String password) {
        users.put(email, password);
    }

    public boolean logIn(String email, String password) {
        if (middleware.check(email, password)) {
            System.out.println("LogIn was success!");
            return true;
        }
        return false;
    }

    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }

    public static boolean hasEmail(String email) {
        return users.containsKey(email);
    }
}
