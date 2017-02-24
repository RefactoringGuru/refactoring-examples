package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

public abstract class Checker {
    public Checker next;

    public abstract boolean check(String email);
}
