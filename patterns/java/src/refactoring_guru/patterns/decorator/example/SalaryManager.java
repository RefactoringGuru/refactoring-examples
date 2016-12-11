package refactoring_guru.patterns.decorator.example;

import refactoring_guru.patterns.decorator.example.decorators.DataSource;

import java.io.File;
import java.util.Scanner;

public class SalaryManager {
    private DataSource source;
    private File data;

    public SalaryManager(DataSource source, File data) {
        this.source = source;
        this.data = data;
    }

    public String load() {
        return source.readData(data);
    }

    public void save() {
        String salaryRecords = new Scanner(System.in).nextLine();
        source.writeData(salaryRecords);
    }
}
