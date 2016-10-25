package refactoring_guru.patterns.factory_method.example.buttons;

import javax.swing.*;
import java.awt.*;

public class WindowsButton implements Button {

    public void render() {
        JPanel panel = new JPanel();

        JButton button = new JButton("Test Button");

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Hello World!");
        label.setOpaque(true);
        label.setBackground(new Color(235, 233, 126));
        label.setFont(new Font("Dialog", Font.BOLD, 44));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.getContentPane().add(panel);
        panel.add(label);
        panel.add(button);

        frame.setSize(320, 200);
        frame.setVisible(true);
    }

    public void onClick(boolean action) {

    }
}
