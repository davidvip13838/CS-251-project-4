package CommonUtilsTest.UsefulObjects;

import CommonUtils.Interfaces.DisjointSetInterface;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * For comparison in the complex tests
 */
public class CorrectDisjointSet implements DisjointSetInterface {
    int[] parent, size;

    /**
     * Initializes a disjoint set of size n
     *
     * @param n size of disjoint set
     * @throws IllegalArgumentException if passed an invalid size (<0)
     */
    public CorrectDisjointSet(int n) throws IllegalArgumentException {

        if (n < 0) throw new IllegalArgumentException("Error: attempt to initialize DisjointSet with n < 0, n=" + n);

        parent = IntStream.range(0, n).toArray();
        size = new int[n];
        Arrays.fill(size, 1);
    }

    /**
     * Checks if given index is out of bounds of the disjoint set and throws if not
     *
     * @param x index to check
     * @throws IndexOutOfBoundsException if x is out of bounds
     */
    private void checkAndThrowOutOfBounds(int x) throws IndexOutOfBoundsException {
        if (x < 0 || parent.length <= x)
            throw new IndexOutOfBoundsException("Error: index " + x +
                    " out of bounds (max idx = " + (parent.length - 1) + ")");
    }

    /**
     * Finds the root of x.
     * <p>
     * <bold>251 students: it is recommended to use path compression (i.e. setting the parent of
     * every node on the path from x to root(x) to be root(x))</bold>
     *
     * @param x node to find the root of
     * @return root of x
     * @throws IndexOutOfBoundsException if x is out of bounds
     */
    @Override
    public int find(int x) throws IndexOutOfBoundsException {
        checkAndThrowOutOfBounds(x);

        //recursive version (trades recursion for less memory usage) (simpler)
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    /**
     * Unions the two sets containing x and y.  Joins the smaller size to the larger size,
     * or if they are the same size, joins x to y (makes y the parent of x, etc.).  Does
     * not change information about any node except possibly the roots of each set.
     *
     * @param x node in set 1
     * @param y node in set 2
     * @throws IndexOutOfBoundsException if x or y are out of bounds
     */
    @Override
    public void union(int x, int y) throws IndexOutOfBoundsException {
        checkAndThrowOutOfBounds(x);

        int smaller = find(x), bigger = find(y);
        //already in same set
        if (smaller == bigger)
            return;

        //determine bigger set
        if (size[bigger] < size[smaller]) {
            int temp = bigger;
            bigger = smaller;
            smaller = temp;
        }
        parent[smaller] = bigger;
        size[bigger] += size[smaller];
    }

    /**
     * Returns the size of the set that node x is contained in
     *
     * @param x node to identify the desired set
     * @return size of the set containing x
     * @throws IndexOutOfBoundsException if x is out of bounds
     */
    @Override
    public int getSetSize(int x) throws IndexOutOfBoundsException {
        checkAndThrowOutOfBounds(x);
        return size[find(x)];
    }

    /**
     * Returns the size of the disjoint set (total number of elements)
     *
     * @return size of disjoint set
     */
    @Override
    public int getDSSize() {
        return parent.length;
    }

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     *
     * @param g graphics object to draw on
     */
    @Override
    public void draw(Graphics g) {
        //DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if (g != null) g.getColor();
        //todo GRAPHICS DEVELOPER:: draw the disjoint set how we discussed
        //251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     *
     * @param g graphics object to draw on
     */
    @Override
    public void visualize(Graphics g) {
        //DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if (g != null) g.getColor();
        //todo GRAPHICS DEVELOPER:: visualization is to be time-based -- how we discussed
        //251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }
}
