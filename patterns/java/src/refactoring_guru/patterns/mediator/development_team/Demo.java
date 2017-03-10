package refactoring_guru.patterns.mediator.development_team;

import refactoring_guru.patterns.mediator.development_team.employees.*;
import refactoring_guru.patterns.mediator.development_team.mediator.Manager;

public class Demo {
    public static void main(String[] args) {
        BackEndDeveloper backEndDeveloper = new BackEndDeveloper();
        QA qa = new QA();
        FrontEndDeveloper frontEndDeveloper = new FrontEndDeveloper();
        new Manager(backEndDeveloper, qa, frontEndDeveloper);
        backEndDeveloper.createProject();
    }
}
