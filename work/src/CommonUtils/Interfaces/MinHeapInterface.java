package CommonUtils.Interfaces;

/**
 * Interface for our new MinHeap object.
 *
 *  Note that we will not intentionally throw empty queue exceptions here.  This is because the use
 *    cases for heaps are much more varied, and it is helpful to be able to peek with nothing there.
 *    Java's libraries were referenced for these reasonings.
 *
 * <bold>251 students: You are explicitly forbidden from using java.util.Queue and any subclass
 *   (including PriorityQueue, for example).  Write your own implementation of a MinHeap.</bold>
 *
 * @param <E> Type of object the heap is holding
 */
public interface MinHeapInterface<E extends Comparable<E>> {
    /**
     * Adds the item to the min heap
     * @param item item to add
     * @throws NullPointerException if the specified element is null
     */
    void add(E item);

    /**
     * Empties the heap.
     */
    void clear();

    /**
     * Returns the minimum element without removing it, or returns <code>null</code> if heap is empty
     * @return the minimum element in the heap, or <code>null</code> if heap is empty
     */
    E peekMin();

    /**
     * Remove and return the minimum element in the heap, or returns <code>null</code> if heap is empty
     * @return the minimum element in the heap, or <code>null</code> if heap is empty
     */
    E removeMin();

    /**
     * Returns the number of elements in the heap
     * @return integer representing the number of elements in the heap
     */
    int size();

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     * @param g graphics object to draw on
     */
    void draw(java.awt.Graphics g);
}
