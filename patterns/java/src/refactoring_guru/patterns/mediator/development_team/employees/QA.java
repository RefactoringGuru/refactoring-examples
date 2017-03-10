package refactoring_guru.patterns.mediator.development_team.employees;

import refactoring_guru.patterns.mediator.development_team.mediator.Mediator;
import refactoring_guru.patterns.mediator.development_team.projects.Project;

import java.util.Random;

public class QA {
    private Mediator manager;

    public void setMediator(Mediator mediator) {
        this.manager = mediator;
    }

    public void testBackEnd(Project project) {
        System.out.println("Testing back end part:\n");
        long currentTime = System.currentTimeMillis();
        project.execute();
        if ((System.currentTimeMillis() - currentTime) < 3_000) {
            System.out.println("BackEnd tests success\n");
            manager.developmentFrontEnd(project);
        } else {
            System.out.println("Some test fail, need revision\n");
            manager.needRevision();
        }
    }

    public void testFrontEnd(Project project) {
        System.out.println("Testing front end part:");
        int result = new Random().nextInt(3);
        if (result == 1) {
            System.out.println("All tests success\n");
            manager.release(project);
        } else {
            System.out.println("Some test fail, need revision\n");
            manager.needNewGUI();
        }
    }
}
