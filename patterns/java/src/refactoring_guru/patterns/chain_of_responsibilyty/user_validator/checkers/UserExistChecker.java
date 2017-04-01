package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

import refactoring_guru.patterns.chain_of_responsibilyty.user_validator.*;

public class UserExistChecker extends Checker {

    @Override
    public boolean check(String email) {
        if (Server.users.containsKey(email)) {
            System.out.println("LogIn is successful");
            return true;
        } else {
            System.out.println("Wrong email or password!");
            return false;
        }
    }
}
