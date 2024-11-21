package CommonUtils.test.UsefulObjects;

import java.util.Arrays;
import java.util.Vector;

/**
 * Just basic object used to test multiple field comparisons
 * Sorts based on first the string, then the integer, then the outer size of the vector
 */
public class ComplexObject implements Comparable<ComplexObject> {
    String a;
    Integer b;
    Vector<Vector<Integer>> c;
    public ComplexObject(String aa, Integer bb, Vector<Vector<Integer>> cc){ a = aa; b = bb; c = cc; }

    public ComplexObject(String aa, Integer bb){
        a = aa;
        b = bb;
        c = new Vector<>(Arrays.asList(
                new Vector<>(Arrays.asList(1, 2)),
                new Vector<>(Arrays.asList(4, 5, 6)),
                new Vector<>(Arrays.asList(7, 8, 9, 10)))); }
    public ComplexObject() {
        a = "za";
        b = Character.getNumericValue('z') - 1;
        c = new Vector<>(Arrays.asList(
                new Vector<>(Arrays.asList(1, 2)),
                new Vector<>(Arrays.asList(4, 5, 6)),
                new Vector<>(Arrays.asList(7, 8, 9, 10))));
    }

    /**
     * Compares purely based on length of outer list just for funsies
     */
    public int vectorCompareTo(Vector<Vector<Integer>> first, Vector<Vector<Integer>> second){
        return first.size() - second.size();
    }
    /**
     * Javadoc is too long
     * negative = <
     * 0 = equal
     * positive = >
     */
    @Override
    public int compareTo(ComplexObject o) {
        return (a.compareTo(o.a) != 0) ? a.compareTo(o.a) :
                (b.compareTo(o.b) != 0) ? b.compareTo(o.b) : vectorCompareTo(c, o.c);
    }

    /**
     * Javadoc is nonexistent.  Uses compareTo, just in case students tried to use the equals method.
     * @param o other object
     * @return if the objects are equal, based on compareTo
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplexObject)) return false;
        ComplexObject that = (ComplexObject) o;
        return this.compareTo(that) == 0;
    }
}