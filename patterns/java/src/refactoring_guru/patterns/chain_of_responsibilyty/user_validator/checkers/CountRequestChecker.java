package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

public class CountRequestChecker extends Checker {
    public int request;

    public CountRequestChecker() {
        this.config = 1;
    }

    @Override
    public boolean check() {
        request++;
        if (request > 3) {
            return false;
        }
        return true;
    }
}
