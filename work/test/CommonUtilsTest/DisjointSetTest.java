package CommonUtilsTest;

import CommonUtils.DisjointSet;
import CommonUtils.Interfaces.DisjointSetInterface;
import CommonUtilsTest.UsefulObjects.CorrectDisjointSet;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the DisjointSet class.  Extensively.  According to the interface specifications.
 *
 * Contains a reference disjoint set class at the bottom of the file.
 *
 * 
 */
public class DisjointSetTest {
    /**
     * Compares the student's answer against the correct answer
     * @param trueAns correct answer
     * @param ans answer to check
     * @param testName name of test in case of failure
     */
    protected void checkDSAgainstCorrect(DisjointSetInterface trueAns, DisjointSetInterface ans, String testName){
        String failurePrefix = "Test case \"" + testName + "\":: ";
        //check size
        assertEquals(trueAns.getDSSize(), ans.getDSSize(),
                failurePrefix + "Incorrect size, expected " + trueAns.getDSSize() +
                        ", actual is " + ans.getDSSize());

        //check each element via getSetSize and find
        for(int i=0; i<trueAns.getDSSize(); i++){
            assertEquals(trueAns.getSetSize(i), ans.getSetSize(i),
                    failurePrefix + "\nIncorrect set size (getSetSize()) for element " + i +
                    ",\nexpected is " + trueAns.getSetSize(i) + ", actual is " + ans.getSetSize(i));

            assertEquals(trueAns.find(i), ans.find(i),
                    failurePrefix + "\nIncorrect root (find()) for element " + i +
                            ",\nexpected is " + trueAns.find(i) + ", actual is " + ans.find(i));
        }
    }


    /**
     * Sanity check.  Failing this should fail (most) all other test cases.
     */
    @Test
    void sanityCheck(){
        int n = 5;
        DisjointSet ds = new DisjointSet(n);

        //size check
        assertEquals(n, ds.getDSSize());
        //initialization check
        for(int i=0; i<n; i++){
            assertEquals(i, ds.find(i));
            assertEquals(1, ds.getSetSize(i));
        }
        ds.union(0, 1);
        assertEquals(1, ds.find(0));
        assertEquals(1, ds.find(1));
        assertEquals(2, ds.find(2));
        assertEquals(3, ds.find(3));
        assertEquals(4, ds.find(4));
        assertEquals(2, ds.getSetSize(1));

        ds.union(0, 4);
        assertEquals(1, ds.find(0));
        assertEquals(1, ds.find(1));
        assertEquals(2, ds.find(2));
        assertEquals(3, ds.find(3));
        assertEquals(1, ds.find(4));
        assertEquals(3, ds.getSetSize(1));
        assertEquals(3, ds.getSetSize(4));

        ds.union(3, 2);
        assertEquals(1, ds.find(0));
        assertEquals(1, ds.find(1));
        assertEquals(2, ds.find(2));
        assertEquals(2, ds.find(3));
        assertEquals(1, ds.find(4));
        assertEquals(2, ds.getSetSize(2));

        ds.union(1, 2);
        assertEquals(1, ds.find(0));
        assertEquals(1, ds.find(1));
        assertEquals(1, ds.find(2));
        assertEquals(1, ds.find(3));
        assertEquals(1, ds.find(4));
        assertEquals(5, ds.getSetSize(3));
    }

    /**
     * Tests for errors being thrown when they should be
     */
    @Nested
    class ErrorTests {
        /**
         * Tests functions that should throw index out of bounds exceptions (find, union, getSetSize)
         */
        @Nested
        class IndexOutOfBoundsErrorSuite {
            /**
             * Defines what numbers are bad indices and tests for throwing
             */
            private abstract class BadIndexTest {
                protected DisjointSet ds = null;
                private final int n = 2000;

                /**
                 * setup
                 */
                @BeforeEach
                void setup(){
                    ds = new DisjointSet(n);
                }

                /**
                 * Tests target function with bad index
                 * @param badNum size to test
                 */
                @ParameterizedTest(name = "Invalid num = {0}")
                @ValueSource(ints = {-1, -1*Integer.MAX_VALUE, -50, n, n*2, n*400, n*20-13})
                void testBadIdx(int badNum){
                    assertThrows(IndexOutOfBoundsException.class, () -> targetFunction(badNum));
                }

                /**
                 * Factory method for calling the desired function that should fail.
                 * @param badNum bad num to test
                 * @throws RuntimeException desired behavior
                 */
                protected abstract void targetFunction(int badNum) throws RuntimeException;
            }

            /**
             * Tests find errors
             */
            @Nested
            class FindErrorTest extends BadIndexTest {
                /**
                 * Factory method for calling the desired function that should fail.
                 *
                 * @param badNum bad num to test
                 * @throws RuntimeException desired behavior
                 */
                @Override
                protected void targetFunction(int badNum) throws RuntimeException {
                    ds.find(badNum);
                }
            }

            /**
             * Tests union x errors
             */
            @Nested
            class UnionXErrorTest extends BadIndexTest {
                /**
                 * Factory method for calling the desired function that should fail.
                 *
                 * @param badNum bad num to test
                 * @throws RuntimeException desired behavior
                 */
                @Override
                protected void targetFunction(int badNum) throws RuntimeException {
                    ds.union(badNum, 0);
                }
            }

            /**
             * Tests union y errors
             */
            @Nested
            class UnionYErrorTest extends BadIndexTest {
                /**
                 * Factory method for calling the desired function that should fail.
                 *
                 * @param badNum bad num to test
                 * @throws RuntimeException desired behavior
                 */
                @Override
                protected void targetFunction(int badNum) throws RuntimeException {
                    ds.union(0, badNum);
                }
            }

            /**
             * Tests getSetSize errors
             */
            @Nested
            class GetSetSizeErrorTest extends BadIndexTest {
                /**
                 * Factory method for calling the desired function that should fail.
                 *
                 * @param badNum bad num to test
                 * @throws RuntimeException desired behavior
                 */
                @Override
                protected void targetFunction(int badNum) throws RuntimeException {
                    ds.getSetSize(badNum);
                }
            }
        }

        /**
         * Tests that the constructor throws when it should
         * @param badNum bad number
         */
        @ParameterizedTest(name = "Invalid num = {0}")
        @ValueSource(ints = {-1, -1*Integer.MAX_VALUE, -50})
        void constructorErrorTest(int badNum){
            assertThrows(IllegalArgumentException.class, () -> new DisjointSet(badNum));
        }
    }

    /**
     * Tests the constructor actually does what it should do
     */
    @Nested
    class ConstructorTests {
        /**
         * Tests that the constructor doesn't fail for unsigned integer bounds via find, getSetSize, and getDSSize
         *
         * @param initSize size to initialize to
         */
        @ParameterizedTest(name = "Size = {0}")
        @ValueSource(ints = {0, 1, 2, 10, 100, 256, 2048, 1000000, 10000000})
        void testConstructorSuccess(int initSize){
            DisjointSet ds = new DisjointSet(initSize);
            assertEquals(initSize, ds.getDSSize());
            for(int i=0; i<initSize; i++){
                assertEquals(i, ds.find(i));
                assertEquals(1, ds.getSetSize(i));
            }
        }
    }

    /**
     * Tests union via a bunch of patterns and for a few different sizes
     */
    @DisplayName("Union test suite!  This may take awhile (around a minute)")
    @Nested
    class UnionTestSuite {
        DisjointSet ds = null;
        CorrectDisjointSet trueDS = null;
        private final int MEDIUM_SIZE = 1000000, SMALL_SIZE0=5000, SMALL_SIZE1=7501;

        /** teardown (help java know it is ok to garbage collect)*/
        @AfterEach
        void teardown(){ ds = null; trueDS = null; }

        /**
         * Tests union in order
         * @param N size to test
         */
        @ParameterizedTest(name = "In order union, n={0}")
        @ValueSource(ints = {MEDIUM_SIZE, SMALL_SIZE0, SMALL_SIZE1})
        void unionInOrder(final int N){
            DisjointSet ds = new DisjointSet(N);
            CorrectDisjointSet trueDS = new CorrectDisjointSet(N);
            for(int i=0; i<N-1; i++){
                ds.union(i, i+1);
                trueDS.union(i, i+1);
                if(N <= SMALL_SIZE1){
                    checkDSAgainstCorrect(trueDS, ds, "Union in order, joining " + i + " and " + (i+1));
                }
            }
            checkDSAgainstCorrect(trueDS, ds, "Union in order, post union check");
        }

        /**
         * Tests union in reverse order
         * @param N size to test
         */
        @ParameterizedTest(name = "Union reverse order, n={0}")
        @ValueSource(ints = {MEDIUM_SIZE, SMALL_SIZE0, SMALL_SIZE1})
        void unionReverse(final int N){
            DisjointSet ds = new DisjointSet(N);
            CorrectDisjointSet trueDS = new CorrectDisjointSet(N);
            for(int i=N-1; i>0; i--){
                ds.union(i, i-1);
                trueDS.union(i, i-1);
                if(N <= SMALL_SIZE1){
                    checkDSAgainstCorrect(trueDS, ds, "Union reverse order, joining " + i + " and " + (i-1));
                }
            }
            checkDSAgainstCorrect(trueDS, ds, "Union reverse order, post union check");
        }

        /**
         * Tests union evens then odds
         */
        @ParameterizedTest(name = "UnionEvenOdd size={0}:: May take awhile")
        @ValueSource(ints = {SMALL_SIZE0, SMALL_SIZE1})
        void unionEvenOdd(final int SMALL_SIZE){
            DisjointSet ds = new DisjointSet(SMALL_SIZE);
            CorrectDisjointSet trueDS = new CorrectDisjointSet(SMALL_SIZE);
            //join evens in pairs
            for(int i=0; i<SMALL_SIZE-2; i+=4){
                ds.union(i, i+2);
                trueDS.union(i, i+2);
                checkDSAgainstCorrect(trueDS, ds, "Union even odd, " +
                        "first round of evens, joining " + i + " and " + (i+2));
            }
            //join odds in pairs
            for(int i=1; i<SMALL_SIZE-2; i+=4){
                ds.union(i, i+2);
                trueDS.union(i, i+2);
                checkDSAgainstCorrect(trueDS, ds, "Union even odd, " +
                        "first round of odds, joining " + i + " and " + (i+2));
            }
            //join all evens together
            for(int i=0; i<SMALL_SIZE-2; i+=2){
                ds.union(i, i+2);
                trueDS.union(i, i+2);
                checkDSAgainstCorrect(trueDS, ds, "Union even odd, " +
                        "joining all evens, joining " + i + " and " + (i+2));
            }
            //join all odds together
            for(int i=1; i<SMALL_SIZE-2; i+=2){
                ds.union(i, i+2);
                trueDS.union(i, i+2);
                checkDSAgainstCorrect(trueDS, ds, "Union even odd, " +
                        "joining all odds, joining " + i + " and " + (i+2));
            }
            //join together everything from middle down
            for(int i=(SMALL_SIZE)/2; i>0; i--){
                ds.union(i, i-1);
                trueDS.union(i, i-1);
                checkDSAgainstCorrect(trueDS, ds, "Union even odd, " +
                        "joining all from middle down, joining " + i + " and " + (i-1));
            }
            //join together everything from middle up
            for(int i= (int)Math.ceil(SMALL_SIZE/2.0); i<SMALL_SIZE-1; i++){
                ds.union(i, i+1);
                trueDS.union(i, i+1);
                checkDSAgainstCorrect(trueDS, ds, "Union even odd, " +
                        "joining all from middle up, joining " + i + " and " + (i+1));
            }
            //join them all together
            ds.union(0, SMALL_SIZE-1);
            trueDS.union(0, SMALL_SIZE-1);
            checkDSAgainstCorrect(trueDS, ds, "Union even odd, joined beginning and end");
        }

        /**
         * Tests union using random numbers
         * @param N size to test
         */
        @ParameterizedTest(name = "Random order union, n={0}")
        @ValueSource(ints = {MEDIUM_SIZE, SMALL_SIZE0, SMALL_SIZE1})
        void unionRandom(final int N){
            DisjointSet ds = new DisjointSet(N);
            CorrectDisjointSet trueDS = new CorrectDisjointSet(N);
            Random rand = new Random(0);//KEEP SEED=0 FOR FAIRNESS!!
            int temp1, temp2;
            //MEDIUM_SIZE times, union two sets
            for(int i=0; i<N; i++){
                temp1 = rand.nextInt(N-1);
                temp2 = rand.nextInt(N-1);
                ds.union(temp1, temp2);
                trueDS.union(temp1, temp2);
                if(N <= SMALL_SIZE1){
                    checkDSAgainstCorrect(trueDS, ds, "Union random order, joining " + temp1 + " and " + temp2);
                }
            }
            checkDSAgainstCorrect(trueDS, ds, "Union random, post union check");
        }
    }

    /**
     * Tests every possible permutation of union parameters for 7 unions for an array of size 7
     */
    @Nested
    class ThoroughTests {
        /**
         * returns the next lexographically greater permutation
         * @param p array to permute
         * @return if a lexographically greater permutation could be made
         */
        boolean next_permutation(int[] p) {
            for (int a = p.length - 2; a >= 0; --a)
                if (p[a] < p[a + 1])
                    for (int b = p.length - 1;; --b)
                        if (p[b] > p[a]) {
                            int t = p[a];
                            p[a] = p[b];
                            p[b] = t;
                            for (++a, b = p.length - 1; a < b; ++a, --b) {
                                t = p[a];
                                p[a] = p[b];
                                p[b] = t;
                            }
                            return true;
                        }
            return false;
        }

        /**
         * Tests union for all n^2 combinations of every permutation of a small array.  Exhaustive test.
         */
        @DisplayName("LONG TEST (3.5+ min): All n^2 combinations of every permutation of a small array")
        @Test
        void testAllPermutations(){
            int n=7;
            int[] arr = IntStream.range(0, n).toArray();

            DisjointSet ds;
            CorrectDisjointSet trueDS;
            int testCase = 0;
            do {
                int[] innerArr = IntStream.range(0, n).toArray();
                do {
                    ds = new DisjointSet(n);
                    trueDS = new CorrectDisjointSet(n);

                    //test
                    for(int i=0; i<n; i++){
                        ds.union(arr[i], innerArr[i]);
                        trueDS.union(arr[i], innerArr[i]);
                        checkDSAgainstCorrect(trueDS, ds, "All permutations number " + testCase);
                    }
                    testCase++;
                    ds = null;
                    trueDS = null;
                } while(next_permutation(innerArr));
            } while(next_permutation(arr));
//            System.out.println("Total number of permutation tests: " + testCase);
        }

        /**
         * increments the number in the given base
         * @param n num to increment
         * @param base base to work in
         * @return incremented number
         */
        int incrementBase(int n, int base){
            n++;
            for(int i=1; i<=base; i++){//base number of digits
                int digit = (int) ((n % Math.pow(10, i))/Math.pow(10, i-1));
                if(digit >= base){//if overflow
                    n += (Math.pow(10, i)-(n%(Math.pow(10, i))));
                }
            }
            return n;
        }

        /**
         * Tests all possible 5 unions for a disjoint set of size 5 (5^5^2 = 9 765 625)
         */
        @DisplayName("LONG TEST (35+ sec): test all possible 5 unions on disjoint set size 5")
        @Test
        void testAllPossibleUnions(){
            int n=5, maxNum = 44444, testCase=0;
            DisjointSet ds;
            CorrectDisjointSet trueDS;
            for(int testNum=0; testNum<=maxNum; testNum = incrementBase(testNum, n)){
                for(int innerNum = 0; innerNum <=maxNum; innerNum = incrementBase(innerNum, n)){
                    ds = new DisjointSet(n);
                    trueDS = new CorrectDisjointSet(n);
                    for(int i=0; i<n; i++){
                        int temp1 = (int) ((testNum/Math.pow(10, i))%10), temp2 = (int) ((innerNum/Math.pow(10, i))%10);
                        ds.union(temp1, temp2);
                        trueDS.union(temp1, temp2);
                        checkDSAgainstCorrect(trueDS, ds, "All combinations number " + testCase);
                    }
                    testCase++;
                }
            }
//            System.out.println("Total num of test cases = " + testCase);
        }
    }

    /**
     * Because I want code coverage to be 100%. Yes, it's unnecessary. Yes, I'm doing it anyways.
     * Better to have someone this meticulous than not thorough enough.
     */
    @Test
    void drawVisualizeDummyTestForCodeCoverage(){
        DisjointSet ds = new DisjointSet(3);
        JPanel panel = new JPanel();
        ds.draw(panel.getGraphics());
        ds.visualize(panel.getGraphics());
    }

}
