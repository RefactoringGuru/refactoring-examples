package refactoring_guru.patterns.memento.shape_editor.editor;

import refactoring_guru.patterns.memento.shape_editor.shapes.Shape;
import refactoring_guru.patterns.memento.shape_editor.shapes.CompoundShape;
import refactoring_guru.patterns.memento.shape_editor.commands.Command;

import javax.swing.*;
import java.io.*;
import java.util.Base64;

public class Editor extends JComponent {
    private EditorCanvas canvas;
    private CompoundShape allShapes = new CompoundShape();
    private History history;

    public Editor() {
        canvas = new EditorCanvas(this);
        history = new History();
    }

    public void loadShapes(Shape... shapes) {
        allShapes.clear();
        allShapes.add(shapes);
        canvas.refresh();
    }

    public CompoundShape getShapes() {
        return allShapes;
    }

    public void remember(Command c) {
        history.push(c);
    }

    public void execute(Command c) {
        remember(c);
        c.execute();
    }

    public void undo() {
        Command command = history.getUndo();
        if (command == null) {
            return;
        }
        System.out.println("Undoing: " + command.getName());
        command.undo();
        canvas.repaint();
    }

    public void redo() {
        Command command = history.getRedo();
        if (command == null) {
            return;
        }
        System.out.println("Redoing: " + command.getName());
        command.undo();
        command.execute();
        canvas.repaint();
    }

    public String backup() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.allShapes);
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            return "";
        }
    }

    public void restore(String state) {
        try {
            byte[] data = Base64.getDecoder().decode(state);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            this.allShapes = (CompoundShape) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            System.out.print("ClassNotFoundException occurred.");
        } catch (IOException e) {
            System.out.print("IOException occurred.");
        }
    }
}
