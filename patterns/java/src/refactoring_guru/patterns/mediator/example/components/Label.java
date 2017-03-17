package refactoring_guru.patterns.mediator.example.components;

import refactoring_guru.patterns.mediator.example.mediator.Mediator;

import javax.swing.*;
import java.awt.*;

/**
 * EN: Concrete components don't talk with each other. They have only one
 *     communication channel–sending requests to the mediator.
 *
 * RU: Конкретные компоненты никак не связаны между собой. У них есть только
 *     один канал общения – через отправку уведомлений посреднику.
 */
public class Label extends JLabel implements Component {
    private Mediator mediator;

    public Label() {
        super("Add or select existing note to procced...");
        setFont(new Font("Verdana", Font.PLAIN, 22));
        setVerticalAlignment(Label.CENTER);
        setHorizontalAlignment(Label.CENTER);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public String getName() {
        return "Label";
    }
}
