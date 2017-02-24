package refactoring_guru.patterns.mediator.example.mediator;

import refactoring_guru.patterns.mediator.example.components.*;

import java.util.*;

public class AuthenticationDialog implements Mediator {
    public String title;
    public CheckBox loginOrRegister;
    public TextBox loginUserEmail;
    public TextBox loginPassword;
    public TextBox registrationUsername;
    public TextBox registrationPassword;
    public TextBox registrationEmail;
    public Button ok;
    public Button cancel;
    public Map<String, String> profiles = new HashMap<>();

    public AuthenticationDialog() {
        this.loginOrRegister = new CheckBox(this);
        this.loginUserEmail = new TextBox(this);
        this.loginPassword = new TextBox(this);
        this.registrationUsername = new TextBox(this);
        this.registrationPassword = new TextBox(this);
        this.registrationEmail = new TextBox(this);
        this.ok = new Button(this);
        this.cancel = new Button(this);
    }

    @Override
    public void notify(String type, Component sender) {
        Scanner scanner = new Scanner(System.in);

        if (type.equals("check") && sender == loginOrRegister) {
            if (loginOrRegister.isChecked()) {
                title = "Log in";
            } else {
                title = "Register";
            }
        }

        if (type.equals("click") && sender == ok) {
            if (loginOrRegister.isChecked()) {
                Set<String> users = profiles.keySet();
                if (users.contains(loginUserEmail.getValue())) {
                    System.out.println("Profile with this email - '" + loginUserEmail.getValue() + "' is exist");
                } else {
                    System.out.println("Profile with this email - '" + loginUserEmail.getValue() + "' not exist");
                    System.out.println("Input email: ");
                    String email = scanner.nextLine();
                    System.out.print("Input password: ");
                    String password = scanner.nextLine();
                    profiles.put(email, password);
                    System.out.println("New profile was created");
                }
            }
        }
    }

    public void putProfile(String email, String pass) {
        profiles.put(email, pass);
    }

    public CheckBox getLoginOrRegister() {
        return loginOrRegister;
    }
}
