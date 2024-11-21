package CommonUtils.UsefulContainers;

import java.util.Objects;

/**
 * Java needs to have this, but for now this will do. Compares by <code>a</code> and then by <code>b</code>.
 * @param <A> type of first element
 * @param <B> type of second element
 */
public class Pair<A extends Comparable<A>, B extends Comparable<B>> implements Comparable<Pair<A, B>> {
    public A a;
    public B b;
    //simple constructor
    public Pair(A a, B b){ this.a = a; this.b = b; }

    //default generated equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(a, pair.a) && Objects.equals(b, pair.b);
    }

    //default generated hash code
    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * (rest of javadoc too long)
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Pair<A, B> o) { return this.a.equals(o.a) ? this.b.compareTo(o.b) : this.a.compareTo(o.a); }

    /**
     * This might be helpful for debugging. Default generated one.
     * @return string version of object
     */
    @Override
    public String toString() { return "Pair{" + "a=" + a + ", b=" + b + '}'; }
}
