package refactoring_guru.patterns.flyweight.example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TreeType {
    private Color color;
    private String name;
    private static Canvas content;
    private static int x;
    private static int y;

    public TreeType(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public void draw(Canvas content, int x, int y) {
        setContent(content);
        setX(x);
        setY(y);
        TreeDrawer drawer = new TreeDrawer();
        drawer.launch(TreeDrawer.class);
    }

    public void setContent(Canvas content) {
        TreeType.content = content;
    }

    public static void setX(int x) {
        TreeType.x = x;
    }

    public static void setY(int y) {
        TreeType.y = y;
    }

    public static class TreeDrawer extends Application {
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Drawing Tree");
            Group root = new Group();
            GraphicsContext graphicsContext = content.getGraphicsContext2D();
            graphicsContext.fillRect(x, y, 10, 70);
            graphicsContext.fillOval(x + 5, y - 10, 40, 40);
            graphicsContext.fillOval(x - 35, y - 10, 40, 40);
            graphicsContext.fillOval(x - 15, y - 40, 40, 40);
            root.getChildren().add(content);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }
}
