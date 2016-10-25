package refactoring_guru.patterns.factory_method.example.buttons;

public class HtmlButton implements Button {

    public void render() {
        System.out.println("<button>Test Button</button>");
    }

    public void onClick(boolean action) {

    }
}
