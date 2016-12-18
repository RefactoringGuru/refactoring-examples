package refactoring_guru.patterns.decorator.example.decorators;

import java.io.*;
import java.util.zip.*;

public class CompressionDecorator extends DataSourceDecorator {
    private int compLevel = 6;

    public int getCompressionLevel() {
        return compLevel;
    }

    public void setCompressionLevel(int value) {
        compLevel = value;
    }

    public CompressionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        super.writeData(new String(compress(data)));
    }

    @Override
    public String readData() {
        return super.readData();
    }

    public byte[] compress(String stringData) {
        byte[] data = stringData.getBytes();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            DeflaterOutputStream ds = new DeflaterOutputStream(os, new Deflater(compLevel));
            ds.write(data);
            ds.finish();
            return os.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }
}
