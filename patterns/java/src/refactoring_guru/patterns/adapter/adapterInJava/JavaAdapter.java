package refactoring_guru.patterns.adapter.adapterInJava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class JavaAdapter {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        List<String> list = Arrays.asList(new String[]{"arg1", "arg2", "arg3"});

        List arraylist = new ArrayList();
        Vector v = new Vector();
        v.add("A");
        v.add("B");
        v.add("C");
        v.add("D");
        v.add("E");
        Enumeration e = v.elements();
        arraylist = Collections.list(e);
        System.out.println("Value of returned list: " + arraylist);
    }
}
