package refactoring_guru.patterns.template_method.example.ai;

public abstract class GameAI {
    public int structures = 1;
    public int resources = 1000;
    public boolean enemy = true;
    public int x = 100;
    public int y = 100;

    public void takeTurn() {
        collectResources();
        buildStructures();
        buildUnits();
        attack(x, y);
    }

    public void collectResources() {
        resources += structures * 100;
    }

    public abstract void buildStructures();

    public abstract void buildUnits();

    public void attack(int x, int y) {
        if (!enemy) {
            sendScouts(x, y);
        } else {
            sendWarriors(x, y);
            this.enemy = false;
        }
    }

    public abstract void sendScouts(int x, int y);

    public abstract void sendWarriors(int x, int y);
}
