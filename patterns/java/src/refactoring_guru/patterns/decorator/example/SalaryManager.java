package refactoring_guru.patterns.decorator.example;

import refactoring_guru.patterns.decorator.example.decorators.DataSource;

import java.util.Scanner;

public class SalaryManager {
    private DataSource source;

    public SalaryManager(DataSource source) {
        this.source = source;
    }

    public String load() {
        return source.readData();
    }

    public void save() {
        String salaryRecords = new Scanner(System.in).nextLine();
        source.writeData(salaryRecords);
    }
}
