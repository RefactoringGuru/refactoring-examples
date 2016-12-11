package refactoring_guru.patterns.decorator.example.decorators;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CompressionDecorator extends DataSourceDecorator {

    public CompressionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public File writeData(String data) {
        return compress(super.writeData(data));
    }

    @Override
    public String readData(File file) {
        return super.readData(deCompress(file));
    }

    public File compress(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(new File("D:\\archive.zip"));
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.setLevel(5);
            zos.putNextEntry(new ZipEntry(file.getName()));
            write(new FileInputStream(file), zos);
            zos.close();
            //file.delete();
        } catch (IOException  ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public File deCompress(File file) {
        File ud = new File("D:\\uncompressed_data.dat");
        try {
            ZipInputStream in = new ZipInputStream((new FileInputStream(file)));
            ZipEntry entry = in.getNextEntry();
            OutputStream out = new FileOutputStream(ud);
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
        return ud;
    }

    private void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) >= 0) {
            out.write(buffer, 0, length);
        }
        in.close();
    }
}
