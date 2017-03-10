package refactoring_guru.patterns.mediator.development_team.employees;

import refactoring_guru.patterns.mediator.development_team.mediator.Mediator;
import refactoring_guru.patterns.mediator.development_team.projects.ConsoleUI;
import refactoring_guru.patterns.mediator.development_team.projects.Project;

public class FrontEndDeveloper {
    private Mediator manager;
    Project project;

    public void setMediator(Mediator mediator) {
        this.manager = mediator;
    }

    public void createGUI(Project project) {
        this.project = new ConsoleUI(project);
        provideProject();
    }

    public void provideProject() {
        manager.receiveProjectFromFrontEnd(this.project);
    }
}
