package CommonUtils.Interfaces;

/**
 * Interface for our DisjointSet object.
 *
 * @apiNote uses union by size so that more queries hit closer to the top of the tree (even if
 *   a few queries are made longer because of it -- the majority will hit closer to the top).
 */
public interface DisjointSetInterface {
    /**
     * Finds the root of x.
     * <p>
     * <bold>251 students: it is recommended to use path compression (i.e. setting the parent of
     *   every node on the path from x to root(x) to be root(x))</bold>
     *
     * @param x node to find the root of
     * @return root of x
     * @throws IndexOutOfBoundsException if x is out of bounds
     */
    int find(int x) throws IndexOutOfBoundsException;

    /**
     * Unions the two sets containing x and y.  Joins the smaller size to the larger size,
     *   or if they are the same size, joins x to y (makes y the parent of x, etc.).  Does
     *   not change information about any node except possibly the roots of each set.
     *
     * @param x node in set 1
     * @param y node in set 2
     * @throws IndexOutOfBoundsException if x or y are out of bounds
     */
    void union(int x, int y) throws IndexOutOfBoundsException;

    /**
     * Returns the size of the set that node x is contained in
     * @param x node to identify the desired set
     * @return size of the set containing x
     * @throws IndexOutOfBoundsException if x is out of bounds
     */
    int getSetSize(int x) throws IndexOutOfBoundsException;

    /**
     * Returns the size of the disjoint set (total number of elements)
     * @return size of disjoint set
     */
    int getDSSize();

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     * @param g graphics object to draw on
     */
    void draw(java.awt.Graphics g);

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     * @param g graphics object to draw on
     */
    void visualize(java.awt.Graphics g);
}
