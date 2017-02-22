package refactoring_guru.patterns.template_method.data_parser;

import java.io.*;

public class CSVDataParser extends DataParser {
    private final StringBuilder builder = new StringBuilder();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void readData() {
        String data = "";
        System.out.println("Enter data:");
        do {
            try {
                data = reader.readLine();
                builder.append(data).append("\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } while (!data.equals(""));
    }

    public void processData() {
        // Можно обрабатывать данные по желанию клиента
    }

    public void writeData() {
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(
                    new File("../java/src/refactoring_guru/patterns/template_method/data_parser/Output.csv")));
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
