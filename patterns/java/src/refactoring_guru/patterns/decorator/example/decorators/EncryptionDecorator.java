package refactoring_guru.patterns.decorator.example.decorators;

import java.util.Scanner;

public class EncryptionDecorator extends DataSourceDecorator {

    public EncryptionDecorator(FileDataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        super.writeData(encode(data));
    }

    @Override
    public String readData() {
        return decode(super.readData());
    }

    public String getNonDecryptedData() {
        return super.readData();
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
