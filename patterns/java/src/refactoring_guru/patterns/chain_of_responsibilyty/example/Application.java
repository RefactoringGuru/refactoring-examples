package refactoring_guru.patterns.chain_of_responsibilyty.example;

public class Application {

    public static Panel createElements() {
        Panel panel = new Panel(0, 0, 400, 800);
        Button ok = new Button(250, 760, 50, 20, "OK");
        ok.onClick = new Function() {
            @Override
            void doJob() {
                System.out.println("Button 'OK' does something");
            }
        };
        panel.add(ok);
        Button cancel = new Button(320, 760, 50, 20, "Cancel");
        cancel.onClick = new Function() {
            @Override
            void doJob() {
                System.out.println("Button 'Cancel' does something");
            }
        };
        panel.add(cancel);
        return panel;
    }

    public static void test(Panel panel) {
        panel.click(251, 761);
    }

    public static void main(String[] args) {
        test(createElements());
    }
}
