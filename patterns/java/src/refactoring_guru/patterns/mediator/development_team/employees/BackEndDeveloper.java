package refactoring_guru.patterns.mediator.development_team.employees;

import refactoring_guru.patterns.mediator.development_team.mediator.Mediator;
import refactoring_guru.patterns.mediator.development_team.projects.Project;

public class BackEndDeveloper {
    private Mediator manager;
    private Project project;

    public void setMediator(Mediator mediator) {
        this.manager = mediator;
    }

    public void createProject() {
        this.project = new Project();
        project.setM(4);
        project.setN(5);
        project.setVeryImportantSetting(150);
        provideProject();
    }

    public void provideProject() {
        manager.receiveProjectFromBackEnd(project);
    }

    public void revisionProject() {
        long revision = project.getVeryImportantSetting() - 50;
        project.setVeryImportantSetting(revision);
        provideProject();
    }
}
