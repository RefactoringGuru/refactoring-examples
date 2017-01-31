package refactoring_guru.patterns.chain_of_responsibilyty.example;

abstract class Component {
    public Function onClick;
    public int x;
    public int y;
    public int width;
    public int height;
    public String name;

    public void click(int x, int y) {
        if (onClick != null) {
            onClick.doJob();
        }
    }

    public abstract void render();

    // getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
