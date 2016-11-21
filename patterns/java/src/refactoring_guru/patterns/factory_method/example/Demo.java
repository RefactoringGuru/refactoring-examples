package refactoring_guru.patterns.factory_method.example;

import refactoring_guru.patterns.factory_method.example.factory.Dialog;
import refactoring_guru.patterns.factory_method.example.factory.HtmlDialog;
import refactoring_guru.patterns.factory_method.example.factory.WindowsDialog;

public class Demo {
    private static Dialog dialog;

    public static void main(String[] args) {
        configure();
        dialog.renderWindow();
    }

    public static void configure() {
        if (System.getProperty("os.name").equals("Windows 10")) {
            dialog = new WindowsDialog();
        } else {
            dialog = new HtmlDialog();
        }
    }
}