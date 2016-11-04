package refactoring_guru.patterns.decorator.example.decorators;

import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CompressionDecorator extends DataSourceDecorator {
    FileDataSource fileDataSource;

    public CompressionDecorator(FileDataSource source) {
        super(source);
        this.fileDataSource = source;
    }

    @Override
    public void writeData(String data) {
        super.writeData(data);
        try {
            File file = new File(fileDataSource.path + fileDataSource.name);
            FileOutputStream fos = new FileOutputStream(new File(fileDataSource.path + "archive.zip"));
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.setLevel(5);
            zos.putNextEntry(new ZipEntry(fileDataSource.name));
            write(new FileInputStream(file), zos);
            zos.close();
            file.delete();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) >= 0) {
            out.write(buffer, 0, length);
        }
        in.close();
    }

    @Override
    public String readData() {
        try {
            File file = new File(fileDataSource.path + "archive.zip");
            ZipInputStream in = new ZipInputStream((new FileInputStream(file)));
            ZipEntry entry = in.getNextEntry();
            OutputStream out = new FileOutputStream(fileDataSource.path + entry.getName());
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return super.readData();
    }
}
