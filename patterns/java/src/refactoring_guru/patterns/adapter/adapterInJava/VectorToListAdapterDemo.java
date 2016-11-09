package refactoring_guru.patterns.adapter.adapterInJava;

import java.util.*;

public class VectorToListAdapterDemo {
    public static void main(String[] args) {
        // So if the transfer enumeration to the list, you can see what's inside
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
