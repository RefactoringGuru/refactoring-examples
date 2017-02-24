package refactoring_guru.patterns.chain_of_responsibilyty.user_validator;

import refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers.*;

import java.io.*;
import java.util.*;

/**
 * RU:
 */
public class Server {
    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private Checker checker;
    public static Map<String, User> users = new HashMap<>();


    public Server() {
        User admin = new User("admin", "admin.@example.com", "root");
        admin.setAdmin(true);
        users.put("admin@example.com", admin);
        checkConfig();
    }

    public void checkConfig() {
        checker = new CountRequestChecker();
        checker.next = new UserExistChecker();
    }

    public void register() {
        try {
            System.out.print("Enter name: ");
            String name = READER.readLine();
            System.out.print("Enter email: ");
            String email = READER.readLine();
            System.out.print("Enter password: ");
            String password = READER.readLine();
            System.out.print("Confirm password: ");
            password.equals(READER.readLine());
            User user = new User(name, email, password);
            users.put(password, user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void logIn() {
        try {
            boolean check = false;
            String email = null;
            String password = null;
            User user;
            while (!check) {
                // checker request here
                check = checker.check(email, password);
                if (check) {
                    System.out.println("Request limit exceeded!");
                    break;
                }
                System.out.print("Enter email: ");
                email = READER.readLine();
                System.out.print("Input password: ");
                password = READER.readLine();

                // checker user here
                if (checker.check(email, password) &&
                        password.equals(users.get(email).getPassword())) {
                    user = users.get(email);
                    System.out.println("LogIn is successful");
                } else {
                    System.out.println("Wrong email or password!b");
                    email = null;
                    password = null;
                    continue;
                }

                // checker admin here
                if (user.isAdmin()) {
                    System.out.println("Hello, Admin!");
                } else {
                    System.out.println("Hello, " + user.getName() + "!");
                }
                check = true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
