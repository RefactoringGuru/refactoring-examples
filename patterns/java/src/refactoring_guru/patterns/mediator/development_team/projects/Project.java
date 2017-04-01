package refactoring_guru.patterns.mediator.development_team.projects;

import java.util.Random;

public class Project {
    private int m;
    private int n;
    private long veryImportantSetting;
    private final Random RANDOM = new Random();

    // some business logic
    public void execute() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(RANDOM.nextInt(100) + " ");
                try {
                    Thread.sleep(veryImportantSetting);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println();
        }
    }

    public void createUI() {
        //
    }

    public void setVeryImportantSetting(long veryImportantSetting) {
        this.veryImportantSetting = veryImportantSetting;
    }

    public long getVeryImportantSetting() {
        return veryImportantSetting;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setN(int n) {
        this.n = n;
    }
}
