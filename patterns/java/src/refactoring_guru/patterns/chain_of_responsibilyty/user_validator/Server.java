package refactoring_guru.patterns.chain_of_responsibilyty.user_validator;

import refactoring_guru.patterns.chain_of_responsibilyty.user_validator.checkers.*;

import java.io.*;
import java.util.*;

public class Server {
    private final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private Checker[] checkers = new Checker[3];
    public static Map<String, User> users = new HashMap<>();


    public Server() {
        User admin = new User("admin", "admin.@example.com", "root");
        users.put("admin@example.com", admin);
        checkers[0] = new CountRequestChecker();
        checkers[1] = new UserExistChecker();
        checkers[2] = new UserAdminChecker();
    }

    public void register() {
        try {
            System.out.print("Enter name: ");
            String name = READER.readLine();
            System.out.print("Enter email: ");
            String email = READER.readLine();
            System.out.print("Enter password: ");
            String password = READER.readLine();
            User user = new User(name, email, password);
            users.put(password, user);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void logIn() {
        try {
            boolean check = false;
            do {
                System.out.print("Enter email: ");
                String email = READER.readLine();
                System.out.print("Input password: ");
                String password = READER.readLine();
                for (int i = 0; i < checkers.length; i++) {
                    check = checkers[i].check(email);
                    if (!check) {
                        if (i == 2) {
                            check = true;
                        }
                        break;
                    }
                }
            } while (!check);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
