package refactoring_guru.patterns.decorator.example.decorators;

import java.io.*;

public class FileDataSource implements DataSource {
    String path = "D:\\";
    String name;

    public FileDataSource(String name) {
        this.name = name;
    }

    @Override
    public void writeData(String data) {
        try(OutputStream fos = new FileOutputStream(path + name)) {
            fos.write(data.getBytes(), 0, data.length());
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String readData() {
        File file = new File(path + name);
        char[] buffer = null;
        try(FileReader reader = new FileReader(file)) {
            buffer = new char[(int)file.length()];
            reader.read(buffer);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return new String(buffer);
    }
}

