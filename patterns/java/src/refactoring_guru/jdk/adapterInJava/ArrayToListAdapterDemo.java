package refactoring_guru.jdk.adapterInJava;

import java.util.Arrays;
import java.util.List;

public class ArrayToListAdapterDemo {
    public static void main(String[] args) {
        // It generates a list of array-based. The array is used in this case
        // for the internal representation of the list. Thus the connection is
        // maintained between the list and the source array

        String[] a = { "foo", "bar", "baz"};
        List<String> list = Arrays.asList(a);
        System.out.println(list); // [foo, bar, baz]
        a[0] = "aaa";
        System.out.println(list); // [aaa, bar, baz]

        // Often the input method need to send it a list of the elements of
        // which are known in advance (often need to quickly write a TDD-test).
        // But initialization list in-place, directly at the place of the call,
        // in the Java syntax is not beautiful. Therefore, it is possible to
        // cause a syntax myMethod(Arrays.asList ("Kyiv", "Lviv", "Odesa")).
    }
}
