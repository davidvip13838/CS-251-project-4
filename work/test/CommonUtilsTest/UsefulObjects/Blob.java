package CommonUtils.test.UsefulObjects;

import java.util.Objects;

/**
 * Dummy class to test equivalence besides default behavior.  Only compares/hashes on fields s and a.
 */
public class Blob implements Comparable<Blob> {
    String s, t;
    int a, b;
    //default constructor
    public Blob(String s, String t, int a, int b) {
        this.s = s;
        this.t = t;
        this.a = a;
        this.b = b;
    }

    /**
     * based just on string s and int a
     * @param o other object
     * @return if they're equal based on string s and int a
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blob blob = (Blob) o;
        return a == blob.a && Objects.equals(s, blob.s);
    }

    /**
     * based just on string s and int a
     * @return hash code based on string s and int a
     */
    @Override
    public int hashCode() {
        return Objects.hash(s, a);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * Only compares on s and a, in that order.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Blob o) {
        return this.s.equals(o.s) ? this.a - o.a : this.s.compareTo(o.s);
    }
}
