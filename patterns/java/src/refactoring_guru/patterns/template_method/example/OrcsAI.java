package refactoring_guru.patterns.template_method.example;

import java.util.ArrayList;
import java.util.List;

public class OrcsAI extends GameAI {
    private List<Scout> scouts = new ArrayList<>();
    private int armours = 0;
    private List<Warrior> warriors = new ArrayList<>();

    @Override
    public void buildStructures() {
        if (resources >= 100) {
            System.out.println("Build farms, then barracks, then stronghold.");
            this.structures += 1;
            this.resources -= 100;
        }
    }

    @Override
    public void buildUnits() {
        if (resources >= 50 && armours >= 2) {
            buildScout();
            buildWarrior();
        } else {
            if (resources >= 10) {
                buildArmour();
            }
            collectResources();
        }
    }

    @Override
    public void sendScouts(int x, int y) {
        if (scouts.size() > 0) {
            for (Scout scout : scouts) {
                scout.takePosition(x, y);
            }
        }
    }

    @Override
    public void sendWarriors(int x, int y) {
        if (warriors.size() > 0) {
            for (Warrior warrior : warriors) {
                warrior.attackPosition(x, y);
            }
        }
    }

    public void buildArmour() {
        this.armours += 1;
        resources -= 10;
    }

    public void buildScout() {
        this.scouts.add(new Scout());
        resources -= 20;
    }

    public void buildWarrior() {
        this.warriors.add(new Warrior());
        resources -= 20;
    }
}
