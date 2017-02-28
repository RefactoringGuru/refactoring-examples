package refactoring_guru.patterns.chain_of_responsibilyty.user_authenticator;

/**
 * RU: Конкретный элемент цепи обрабатывает запрос по-своему.
 */
public class UserExistsMiddleware extends Middleware {
    public boolean check(String email, String password) {
        if (!Server.hasEmail(email)) {
            return false;
        }
        return checkNext(email, password);
    }
}
