package refactoring_guru.patterns.mediator.development_team.mediator;

import refactoring_guru.patterns.mediator.development_team.employees.*;
import refactoring_guru.patterns.mediator.development_team.projects.Project;

public class Manager implements Mediator {
    private BackEndDeveloper backEnd;
    private FrontEndDeveloper frontEnd;
    private QA qa;
    private Project project;

    public Manager(BackEndDeveloper backEnd, QA qa, FrontEndDeveloper frontEnd) {
        this.backEnd = backEnd;
        this.frontEnd = frontEnd;
        this.qa = qa;

        backEnd.setMediator(this);
        frontEnd.setMediator(this);
        qa.setMediator(this);
    }

    public void receiveProjectFromBackEnd(Project project) {
        this.project = project;
        testBackEndPart();
    }

    public void developmentFrontEnd(Project project) {
        frontEnd.createGUI(project);
    }

    public void receiveProjectFromFrontEnd(Project project) {
        this.project = project;
        testFrontEndPart();
    }

    public void needRevision() {
        backEnd.revisionProject();
    }

    public void needNewGUI() {
        developmentFrontEnd(project);
    }

    public void testBackEndPart() {
        qa.testBackEnd(this.project);
    }

    public void testFrontEndPart() {
        qa.testFrontEnd(this.project);
    }

    public void release(Project project) {
        project.createUI();
    }
}
