package refactoring_guru.patterns.mediator.development_team.projects;

import java.util.Scanner;

public class ConsoleUI extends Project{
    private Scanner scanner = new Scanner(System.in);
    private Project project;

    public ConsoleUI(Project project) {
        this.project = project;
    }

    public void createUI() {
        System.out.println("Console GUI");
        System.out.print("Input m: ");
        int m = scanner.nextInt();
        System.out.print("Input n: ");
        this.project.setM(m);
        int n = scanner.nextInt();
        this.project.setN(n);
        execute();
    }

    public void execute() {
        this.project.execute();
    }
}
