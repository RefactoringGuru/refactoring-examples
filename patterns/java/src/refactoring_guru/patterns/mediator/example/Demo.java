package refactoring_guru.patterns.mediator.example;

import java.util.Scanner;

public class Demo {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AuthenticationDialog dialog = new AuthenticationDialog();
        dialog.putProfile("jack@ya.com", "qwerty");
        dialog.putProfile("big.bob@bing.com", "Gyhjik4862");

        System.out.print("Input user email: ");
        String userEmail = scanner.nextLine();
        dialog.loginUserEmail.setValue(userEmail);
        System.out.print("Input password: ");
        String password = scanner.nextLine();
        dialog.loginPassword.setValue(password);

        dialog.loginOrRegister.setChecked(true);
        dialog.notify("click", dialog.ok);
    }
}
