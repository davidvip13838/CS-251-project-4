package CommonUtils.test.factories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An abstract class for testing anything that should behave well (not throw errors and
 *   return null) in the presence of null requests and requests for nonexistent objects.
 *   Assumes container state should not change when we try to add(key=null).
 *
 * @apiNote Assumes a key-value container with Integer key and Integer value.
 * @implNote Requires container persistence in children.
 * @author Ethan Dickey
 */
public abstract class NullAndNotFoundTestFactory {
    /**
     * Tests that require a full container with verification that the container did not change after the test.
     */
    @Nested
    class FullContainerTests {
        /**
         * Forces the container to be full before each test, in order to check that nothing changed during the test.
         */
        @BeforeEach
        void setupContainer(){
            fillContainer();
        }

        /**
         * Forces the container to be the same as before the tests, according to bounds set by children classes
         */
        @AfterEach
        void verifyNoChange(){
            verifyContainerDidNotChange();
        }

        /**
         * Tests that removing null does not change the state of the table.
         */
        @Test
        void testRemoveNull(){
            try {
                remove(null);
            } catch (Exception e) {
                fail("Threw exception removing null from full container: " + e.getMessage());
            }
        }

        /**
         * Tests that get() returns null if passed null
         */
        @Test
        void testGetNull(){
            try {
                assertNull(get(null));
            } catch (Exception e) {
                fail("Threw exception retrieving null from full container: " + e.getMessage());
            }
        }

        /**
         * Tests that removing a bunch of nonexistent keys does not change the state of the table.
         */
        @Test
        void testRemoveNotExists(){
            try {
                for(var i : getKeysThatDoNotExistInFullContainer()){
                    assertDoesNotThrow(() -> remove(i));
                }
            } catch (Exception e) {
                fail("Threw exception retrieving invalid key from full container: " + e.getMessage());
            }
        }

        /**
         * Tests that get() returns null if the key does not exist
         */
        @Test
        void testGetNotExists(){
            try {
                for(var i : getKeysThatDoNotExistInFullContainer()){
                    assertNull(get(i));
                }
            } catch (Exception e) {
                fail("Threw exception retrieving invalid key from full container: " + e.getMessage());
            }
        }
    }

    /**
     * Tests the assertion that passing key=null will not change the state of the table.  Likely point
     *   of failure if the students just use the Java library default instead of implementing their own container
     *   because not allowing null to change the container is a custom requirement to distinguish the
     *   library implementations.
     */
    @Test
    void testNullInsert(){
        makeNewEmptyContainer();
        try {
            add(null, 3);
            assertEquals(0, getSize(),"MANUAL CHECK REQUIRED:: " +
                    "IF THIS FAILS THEY MIGHT BE USING THE STANDARD LIBRARY FUNCTIONS");
        } catch (Exception e) {
            fail("Adding null to empty container threw an exception");
        }
    }



    /*Functions for container setup and validation*/
    /**
     * Reinitializes the container to be empty.  Useful in NullInsert test.
     */
    protected abstract void makeNewEmptyContainer();

    /**
     * Children must provide functionality to fill their container before each test, as well as a way to
     *   verify that nothing changed.  Does not assume container is initialized.
     *
     * @implNote this function and {@link #verifyContainerDidNotChange()} must agree on what
     *   is in the container.
     */
    protected abstract void fillContainer();

    /**
     * Returns a key that does not exist in the full container, after running {@link #fillContainer()}
     * @return key that does not exist in full container
     */
    protected abstract List<Integer> getKeysThatDoNotExistInFullContainer();

    /**
     * Children must provide functionality to fill their container before each test, as well as a way to
     *   verify that nothing changed.
     *
     * @implNote {@link #fillContainer()} and this function must agree on what is in the container.
     */
    protected abstract void verifyContainerDidNotChange();


    /*Functions for manipulating the container*/
    /**
     * Factory methods for calling the appropriate add function on the container you are testing.
     * @param key key to add with value
     * @param value value to add with key
     * @throws Exception if something goes wrong
     */
    protected abstract void add(Integer key, Integer value) throws Exception;

    /**
     * Factory method for calling the appropriate get(key) function on the container you are testing.
     * @param key key to retrieve the value for
     * @return value associated with the key
     * @throws Exception if something goes wrong
     */
    protected abstract Integer get(Integer key) throws Exception;

    /**
     * Factory method for calling the appropriate parameterized remove function on the container you are testing.
     * @param key key to remove from container
     * @throws Exception if something goes wrong
     */
    protected abstract void remove(Integer key) throws Exception;

    /**
     * Factory methods for calling the appropriate size() function on the container you are testing.
     */
    protected abstract long getSize();
}
