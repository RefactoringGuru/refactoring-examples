package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

import refactoring_guru.patterns.chain_of_responsibilyty.user_validator.*;

public class UserExistChecker extends Checker {

    @Override
    public boolean check(String email) {
        return Server.users.containsKey(email);
    }
}
