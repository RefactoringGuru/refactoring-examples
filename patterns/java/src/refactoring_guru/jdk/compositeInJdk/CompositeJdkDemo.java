package refactoring_guru.jdk.compositeInJdk;

import javax.swing.*;
import java.awt.*;

public class CompositeJdkDemo {
    public static void main(String[] args) {
        Frame frame = compoundComponents();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static JFrame compoundComponents() {
        JFrame frame = new JFrame("Log in");
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        frame.getContentPane().add(content);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent[] components = createComponents();
        for (int i = 0; i < components.length; i++) {
            content.add(components[i]);
        }
        frame.setSize(230, 150);
        return frame;
    }

    public static JComponent[] createComponents() {
        JComponent[] components = new JComponent[3];
        JPanel username = new JPanel(new FlowLayout(FlowLayout.CENTER));
        username.add(new JLabel("Username"));
        username.add(new JTextField(10));
        JPanel password = new JPanel(new FlowLayout(FlowLayout.CENTER));
        password.add(new JLabel("Password"));
        password.add(new JTextField(10));
        JPanel login = new JPanel(new FlowLayout(FlowLayout.CENTER));
        login.add(new JCheckBox("Remember me"));
        login.add(new JButton("Log in"));
        components[0] = username;
        components[1] = password;
        components[2] = login;
        return components;
    }
}
