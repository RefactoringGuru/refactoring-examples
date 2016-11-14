package refactoring_guru.patterns.adapter.adapterInJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StreamToReaderAdapterDemo {
    public static void main(String[] args) throws Exception {
        // BufferedReader inherits from Reader, it can use all of the methods for
        // reading from the stream, are defined in the Reader. And also defines its
        // own BufferedReader readLine () method that allows you to read from the
        // stream line by line.
        // BufferedReader reads text from a character input stream, buffering
        // characters read. Using the buffer is designed to increase the performance
        // of data read from the stream.
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String data = in.readLine();
        System.out.println(data);
    }
}
