package refactoring_guru.patterns.chain_of_responsibilyty.example;

public class Panel extends ContainerComponent {

    public Panel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void render() {
        System.out.println("Panel" + "\n"
                + "Position:" + "\n" + "x - " + this.x + "\n" + "y - " + this.y + "\n" +
                "width - " + this.width + "\n" + "height - " + this.height);
    }

    public void click(int x, int y) {
        Button button = null;
        for(Component findButton : children) {
            if ((x > findButton.getX()) && (x < x + findButton.getWidth())
                    && (y > findButton.getY()) && (y < y + findButton.getHeight())) {
                button = (Button)findButton;
            }
        }

        if (button != null) {
            button.click(x, y);
        } else {
            System.out.println("Click on the button was out of range");
        }
    }
}
