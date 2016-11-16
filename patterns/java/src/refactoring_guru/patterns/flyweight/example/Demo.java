package refactoring_guru.patterns.flyweight.example;


import java.awt.*;

public class Demo {
    public static void main(String[] args) {
        Forest forest = new Forest();
        forest.plantTree(300, 300, "Dub", Color.ORANGE);
        forest.plantTree(400, 400, "Bereza", Color.GREEN);
        forest.draw();
    }
}
