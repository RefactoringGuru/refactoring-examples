package refactoring_guru.patterns.decorator.example.decorators;

import java.io.File;

public class EncryptionDecorator extends DataSourceDecorator {

    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public File writeData(String data) {
        return super.writeData(encode(data));
    }

    @Override
    public String readData(File file) {
        return decode(super.readData(file));
    }

    public String encode(String data) {
        String key = "guru";
        byte[] text = data.getBytes();
        byte[] keyBytes = key.getBytes();
        byte[] result = new byte[data.length()];
        for (int i = 0; i < text.length; i++) {
            result[i] = (byte)(text[i] ^ keyBytes[i % keyBytes.length]);
        }
        return new String(result);
    }

    public String decode(String data) {
        return encode(data);
    }
}
