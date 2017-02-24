package refactoring_guru.jdk.decoratorInJava;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class JavaDecorator {
    public static void main(String[] args) {
        try {
            File file = new File("D:\\tmp.zip");
            OutputStream fos = new FileOutputStream(file);
            OutputStream bos = new BufferedOutputStream(fos);
            OutputStream zos = new GZIPOutputStream(bos, 1024);
            Writer writer = new OutputStreamWriter(zos, "UTF8");
            writer.write("Hello Decorator");
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
