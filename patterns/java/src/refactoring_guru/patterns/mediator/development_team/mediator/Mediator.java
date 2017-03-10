package refactoring_guru.patterns.mediator.development_team.mediator;

import refactoring_guru.patterns.mediator.development_team.projects.Project;

public interface Mediator {
    void receiveProjectFromBackEnd(Project project);
    void receiveProjectFromFrontEnd(Project project);
    void developmentFrontEnd(Project project);
    void needRevision();
    void needNewGUI();
    void release(Project project);
}
