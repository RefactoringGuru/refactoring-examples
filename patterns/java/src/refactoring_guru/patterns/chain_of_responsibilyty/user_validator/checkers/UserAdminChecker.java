package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

public class UserAdminChecker extends Checker {
    @Override
    public boolean check(String email, String password) {
        if (true) {
            return true;
        } else {
            return false;
        }
    }
}
