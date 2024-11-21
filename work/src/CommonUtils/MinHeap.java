package CommonUtils;

import CommonUtils.Interfaces.MinHeapInterface;

import java.awt.*;
import java.util.Arrays;

/**
 * Implements our MinHeapInterface and adds a constructor
 * <p>
 * <b>251 students: You are explicitly forbidden from using java.util.Queue (including any subclass
 *   like PriorityQueue) and any other java.util.* library EXCEPT java.util.Arrays and java.util.Vector.
 *   Write your own implementation of a MinHeap.</b>
 *
 * @param <E> the type of object this heap will be holding
 */
public class MinHeap<E extends Comparable<E>> implements MinHeapInterface<E> {

    private E[] heap;  // Array to store heap elements
    private int size;  // Current number of elements in the heap
    private static final int INITIAL_CAPACITY = 10;

    /**
     * A recursive method to heapify (sort the root to where it should go) a
     * subtree with the root at given index
     * Assumes the subtrees are already heapified.
     * (The purpose of this method is to balance tree starting at the root)
     *
     * @param i root of the subtree to heapify
     */
    private void heapify(int i) {
        int smallest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // Find the smallest of root, left, and right child
        if (left < size && heap[left].compareTo(heap[smallest]) < 0) {
            smallest = left;
        }

        if (right < size && heap[right].compareTo(heap[smallest]) < 0) {
            smallest = right;
        }

        // If the smallest is not the root, swap and continue heapifying
        if (smallest != i) {
            swap(i, smallest);
            heapify(smallest);
        }
    }

    /**
     * Helper method to swap two elements in the heap
     *
     * @param i index of the first element
     * @param j index of the second element
     */
    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Constructs an empty min heap
     */
    public MinHeap() {
        heap = (E[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the item to the min heap
     *
     * @param item item to add
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void add(E item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }

        // Expand capacity if needed
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }

        // Add the new item at the end
        heap[size] = item;
        size++;

        // Fix the min heap property (bottom-up heapify)
        int i = size - 1;
        while (i > 0 && heap[(i - 1) / 2].compareTo(heap[i]) > 0) {
            swap(i, (i - 1) / 2);  // Swap with parent
            i = (i - 1) / 2;
        }
    }

    /**
     * Empties the heap.
     */
    @Override
    public void clear() {
        heap = (E[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the minimum element without removing it, or returns <code>null</code> if heap is empty
     *
     * @return the minimum element in the heap, or <code>null</code> if heap is empty
     */
    @Override
    public E peekMin() {
        if (size == 0) {
            return null;
        }
        return heap[0];  // The root of the heap is the minimum element
    }

    /**
     * Remove and return the minimum element in the heap, or returns <code>null</code> if heap is empty
     *
     * @return the minimum element in the heap, or <code>null</code> if heap is empty
     */
    @Override
    public E removeMin() {
        if (size == 0) {
            return null;
        }

        // The root is the minimum element
        E min = heap[0];

        // Move the last element to the root and reduce the size
        heap[0] = heap[size - 1];
        size--;

        // Restore the heap property (top-down heapify)
        heapify(0);

        return min;
    }

    /**
     * Returns the number of elements in the heap
     *
     * @return integer representing the number of elements in the heap
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public void draw(Graphics g) {

    }
}
