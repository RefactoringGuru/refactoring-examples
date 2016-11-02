package refactoring_guru.patterns.factory_method.example.buttons;

public class HtmlButton implements Button {

    public void render() {
        System.out.println("<button>Test Button</button>");
        onClick();
    }

    public void onClick() {
        System.out.println("Button say - 'Hello World!'");
    }
}
