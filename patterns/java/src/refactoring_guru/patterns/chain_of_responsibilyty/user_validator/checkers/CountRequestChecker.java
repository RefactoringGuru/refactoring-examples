package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

public class CountRequestChecker extends Checker {
    public int request;

    @Override
    public  boolean check(String email) {
        request++;
        if (request <= 3) {
            return true;
        } else {
            System.out.println("Request limit exceeded!");
            Thread.currentThread().stop();
            return false;
        }
    }
}
