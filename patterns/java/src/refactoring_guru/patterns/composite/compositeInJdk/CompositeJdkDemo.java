package refactoring_guru.patterns.composite.compositeInJdk;

import javax.swing.*;
import java.awt.*;

public class CompositeJdkDemo {
    public static void main(String[] args) {
        Frame frame = compoundComponents();
        frame.setVisible(true);
    }

    public static JFrame compoundComponents() {
        JFrame frame = new JFrame("composite JDK example");
        JPanel content = new JPanel(new GridLayout(2, 2));
        frame.getContentPane().add(content, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent[] components = createComponents();
        for (int i = 0; i < components.length; i++) {
            content.add(components[i]);
        }
        frame.setSize(components.length * 50, components.length * 50);
        return frame;
    }

    public static JComponent[] createComponents() {
        JComponent[] components = new JComponent[6];
        components[0] = new JButton("Button 1");
        components[1] = new JButton("Button 2");
        components[2] = new JButton("Button 3");
        JLabel lbl1 = new JLabel("Label 1");
        lbl1.setSize(100, 50);
        lbl1.setHorizontalAlignment(JLabel.CENTER);
        components[3] = lbl1;
        JLabel lbl2 = new JLabel("Label 2");
        lbl2.setSize(100, 50);
        lbl2.setHorizontalAlignment(JLabel.CENTER);
        components[4] = lbl2;
        JLabel lbl3 = new JLabel("Label 3");
        lbl3.setSize(100, 50);
        lbl3.setHorizontalAlignment(JLabel.CENTER);
        components[5] = lbl3;
        return components;
    }
}
