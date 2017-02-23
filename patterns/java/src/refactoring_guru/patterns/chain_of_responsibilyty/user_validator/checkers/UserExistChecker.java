package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

public class UserExistChecker extends Checker {
    public UserExistChecker() {
        this.config = 2;
    }

    @Override
    public boolean check() {
        return false;
    }
}
