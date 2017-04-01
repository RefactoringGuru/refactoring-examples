package refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers;

import refactoring_guru.patterns.chain_of_responsibilyty.user_validator.Server;

public class UserAdminChecker extends Checker {
    @Override
    public boolean check(String email) {
        if (Server.users.get(email).getName().equals("admin")) {
            System.out.println("Hello, Admin!");
            return true;
        } else {
            System.out.println("Hello, " + Server.users.get(email).getName());
            return false;
        }
    }
}
