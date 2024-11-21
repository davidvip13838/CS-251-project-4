package CommonUtils.UsefulContainers;

/**
 * Contains two ints and a double and compares only on the weight
 */
public class Edge implements Comparable<Edge> {
    public int a, b;
    public double w;

    //simple constructor
    public Edge(int a, int b, double w) {
        this.a = a;
        this.b = b;
        this.w = w;
    }


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * rest of javadoc is too long.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Edge o) { return Double.compare(this.w, o.w); }
}
