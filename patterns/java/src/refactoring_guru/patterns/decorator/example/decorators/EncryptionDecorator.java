package refactoring_guru.patterns.decorator.example.decorators;

public class EncryptionDecorator extends DataSourceDecorator {

    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        super.writeData(encode(data));
    }

    @Override
    public String readData() {
        return super.readData();
    }

    public String readDecodedData() {
        return decode(super.readData());
    }

    public String encode(String data) {
        byte[] result = data.getBytes();
        for (int i = 0; i < result.length; i++) {
            result[i] += (byte)1;
        }
        return new String(result);
    }

    public String decode(String data) {
        byte[] result = data.getBytes();
        for (int i = 0; i < result.length; i++) {
            result[i] -= (byte)1;
        }
        return new String(result);
    }
}
