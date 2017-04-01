package refactoring_guru.patterns.template_method.data_parser;

public abstract class DataParser {
    public void parseDataAndOutput() {
        readData();
        processData();
        writeData();
    }

    abstract void readData();
    abstract void processData();
    abstract void writeData();
}
