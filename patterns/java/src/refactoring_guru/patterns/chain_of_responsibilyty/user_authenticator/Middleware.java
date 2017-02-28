package refactoring_guru.patterns.chain_of_responsibilyty.user_authenticator;

/**
 * RU: Базовый класс цепочки умеет строить цепь.
 */
public abstract class Middleware {
    private Middleware next;

    public Middleware linkWith(Middleware next) {
        this.next = next;
        return next;
    }

    public boolean checkNext(String email, String password) {
        if (next != null) {
            return next.check(email, password);
        }
        return true;
    }

    abstract boolean check(String email, String password);
}
