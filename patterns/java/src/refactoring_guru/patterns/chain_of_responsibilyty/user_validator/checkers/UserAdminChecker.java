package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

public class UserAdminChecker extends Checker {
    public UserAdminChecker() {
        this.config = 3;
    }

    @Override
    public boolean check() {
        return false;
    }
}
