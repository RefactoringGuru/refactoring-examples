package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

public abstract class Checker {
    public Checker next;
    public int config;

    public abstract boolean check();
}
