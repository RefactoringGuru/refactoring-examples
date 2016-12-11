package refactoring_guru.patterns.decorator.example.decorators;

import java.io.*;

public class FileDataSource implements DataSource {
    String name;

    public FileDataSource(String name) {
        this.name = name;
    }

    @Override
    public File writeData(String data) {
        File file = new File(name);
        try(OutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes(), 0, data.length());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return file;
    }

    @Override
    public String readData(File file) {
        char[] buffer = null;
        try(FileReader reader = new FileReader(file)) {
            buffer = new char[(int)file.length()];
            reader.read(buffer);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new String(buffer);
    }
}

