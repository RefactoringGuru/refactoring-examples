package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

public class CountRequestChecker extends Checker {
    public int request;

    @Override
    public boolean check(String name, String password) {
        if (name == null && password == null) {
            request++;
            return request > 3;
        } else {
            return next.check(name, password);
        }
    }
}
