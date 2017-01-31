package refactoring_guru.patterns.chain_of_responsibilyty.example;

public class Button extends Component {

    public Button(int x, int y, int width, int height, String name) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.name = name;
    }

    @Override
    public void render() {
        System.out.println("Button - " + this.name + "\n"
                + "Position:" + "\n" + "x - " + this.x + "\n" + "y - " + this.y + "\n" +
                "width - " + this.width + "\n" + "height - " + this.height);
    }
}
