package refactoring_guru.patterns.chain_of_responsibilyty.user_validator;

import refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers.*;

import java.io.*;
import java.util.*;

public class Server {
    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private Checker checker;
    public Map<String, User> users = new HashMap<>();


    public Server() {
        User admin = new User("admin", "admin.@example.com", "root");
        admin.setType("admin");
        users.put("admin", admin);
        checkConfig(3);
    }

    public void checkConfig(int config) {
        if (config == 1) {
            checker = new CountRequestChecker();
        } else if (config == 2) {
            checker = new CountRequestChecker();
            checker.next = new UserExistChecker();
        } else if (config == 3) {
            checker = new CountRequestChecker();
            checker.next = new UserExistChecker();
            checker.next.next = new UserAdminChecker();
        } else {
            checkConfig(3);
        }
    }

    public void register() {
        try {
            System.out.print("Enter name: ");
            String name = READER.readLine();
            System.out.print("Enter email: ");
            String email = READER.readLine();
            System.out.print("Enter password: ");
            String password = READER.readLine();
            System.out.print("Confirm password: " + password.equals(READER.readLine()));
            User user = new User(name, email, password);
            users.put(password, user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void logIn() {
        try {
            boolean check = true;
            while (check) {
                // checker request here
                System.out.print("Enter email: ");
                String email = READER.readLine();
                System.out.print("Input password: ");
                String password = READER.readLine();

                // checker user here
                User user = users.get(password);
                boolean bool = email.equals(user.getEmail());

                // checker admin here

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
