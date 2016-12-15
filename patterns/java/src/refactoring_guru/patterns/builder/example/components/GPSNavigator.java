package refactoring_guru.patterns.builder.example.components;

public class GPSNavigator {
    private String route;

    public GPSNavigator() {
        this.route = "221b, Baker Street, London  to Scotland Yard, 8-10 Broadway, London";
    }

    public GPSNavigator(String manualRout) {
        this.route = manualRout;
    }

    public String getRoute() {
        return route;
    }
}
