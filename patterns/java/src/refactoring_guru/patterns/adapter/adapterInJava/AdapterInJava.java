package refactoring_guru.patterns.adapter.adapterInJava;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

public class AdapterInJava {
    public static void main(String[] args) {
        String[] arrayString = new String[] {"H", "e", "l", "l", "o"};
        List<String> stringList = Arrays.asList(arrayString); // Static class Arrays is adapter for transform String[] tj list

//      BufferedReader bufferedReader = new BufferedReader(System.in); // CTE incompatible types: java.io.InputStream cannot be converted to java.io.Reader
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); // OK InputStream is adapter for BufferedReader

        // One more example
        try {
            File file = new File("D:\\tmp.zip");
            OutputStream fos = new FileOutputStream(file);
            OutputStream bos = new BufferedOutputStream(fos);
            OutputStream zos = new GZIPOutputStream(bos, 1024);
            Writer writer = new OutputStreamWriter(zos, "UTF8");
            writer.write("Hello Adapter/Decorator");
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
