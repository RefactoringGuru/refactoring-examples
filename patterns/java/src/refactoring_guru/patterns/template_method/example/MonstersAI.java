package refactoring_guru.patterns.template_method.example;

public class MonstersAI extends GameAI {

    @Override
    public void collectResources() {
        System.out.println("Do nothing.");
    }

    @Override
    public void buildStructures() {
        System.out.println("Do nothing.");
    }

    @Override
    public void buildUnits() {
        System.out.println("Do nothing.");
    }

    @Override
    public void sendScouts(int x, int y) {
        System.out.println("Do nothing.");
    }

    @Override
    public void sendWarriors(int x, int y) {
        System.out.println("Do nothing.");
    }
}
