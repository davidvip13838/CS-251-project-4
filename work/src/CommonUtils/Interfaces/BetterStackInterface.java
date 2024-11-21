package CommonUtils.Interfaces;

import java.util.EmptyStackException;

/**
 * Interface for our new BetterStack object.
 *
 * <bold>251 students: You are explicitly forbidden from using java.util.Stack and any
 *   subclass.  Write your own implementation of a Stack.</bold>
 *
 * @param <E> Type of object the stack is holding
 */
public interface BetterStackInterface<E> {
    /**
     * Push an item onto the top of the stack
     * @param item item to push
     */
    void push(E item);

    /**
     * Remove and return the top item on the stack
     * @return the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    E pop();

    /**
     * Returns the top of the stack (does not remove it).
     * @return the top of the stack
     * @throws EmptyStackException if stack is empty
     */
    E peek();

    /**
     * Returns whether the stack is empty
     * @return true if the stack is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the stack
     * @return integer representing the number of elements in the stack
     */
    int size();

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     * @param g graphics object to draw on
     */
    void draw(java.awt.Graphics g);
}
