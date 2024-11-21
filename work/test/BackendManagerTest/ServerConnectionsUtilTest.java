package BackendManagerTest;

import BackendManagers.Interfaces.ServerConnectionsUtilInterface;
import BackendManagers.Interfaces.ServerConnectionsUtilInterface.ServerConnection;
import BackendManagers.ServerConnectionsUtil;
import CommonUtils.UsefulContainers.Pair;
import CommonUtilsTest.UsefulObjects.CorrectDisjointSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import static java.lang.System.out;

/**
 * Tests {@link BackendManagers.ServerConnectionsUtil}.
 * @author
 */
public class ServerConnectionsUtilTest {
    final static String prefix = "work/test/BackendManagerTest/serverConnectionsFiles/";
    final static File[] folderPaths = {new File(prefix + "sample"),
            new File(prefix + "manual"),
            new File(prefix + "generated"),
    };
    final static String inputSuffix = "in", ansSuffix = "out";

    /**
     * All done in one instance of the manager
     */
    ServerConnectionsUtilInterface manager = new ServerConnectionsUtil();


    /**
     * Provides a list of test files and their names for the parameterized test below.
     * @return List of valid test input files and their names
     */
    static Stream<Arguments> testFileProvider(){
        ArrayList<Arguments> args = new ArrayList<>();
        //for all folders provided
        for(final File path : folderPaths){
            //for each file in each folder
            for(final File entry : Objects.requireNonNull(path.listFiles())){
                String inputFile = entry.getPath();
                //if not an input file, skip
                if(! (inputFile.substring(inputFile.length() - inputSuffix.length()).equalsIgnoreCase(inputSuffix))){
                    continue;
                }
                args.add(arguments(Named.of(entry.getName(), entry)));
            }
        }

        return args.stream();
    }


    /**
     * Runs all input files
     */
    @DisplayName("File-based tests for Story 7")
    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testFileProvider")
    void runFiles(File file) {
        String inputFile = file.getPath();

        //guaranteed to have a valid input file
        String ansFile = inputFile.substring(0, inputFile.length() - inputSuffix.length()) + ansSuffix;


        //out.println("Starting test " + inputFile);
        //out.println("  Getting student response, time=0");
        long startTime = System.currentTimeMillis();
        //run test
        List<ServerConnection> ans = null;
        try {
            ans = manager.getMinimumServerConnections(inputFile);
        } catch(Exception e){
            e.printStackTrace();
            fail("Error calling getMinimumServerConnections(\"" + file.getName() + "\": " + e.getMessage());
        }
        //out.println("  Student response ended at " + (System.currentTimeMillis() - startTime) + "ms");

        //compare to answer
        //read in answer file and edges
        //out.println("  Reading answer file at " + (System.currentTimeMillis() - startTime) + "ms");
        BufferedReader bf;
        double trueWeight = -1;
        try {
            bf = new BufferedReader(new FileReader(ansFile));
            trueWeight = Double.parseDouble(bf.readLine());
            bf.close();
        } catch (FileNotFoundException e) {
            fail("GRADER ERROR:: ANSWER FILE NOT FOUND:: \"" + file.getName() + "\"");
        } catch (IOException e) {
            fail("GRADER ERROR:: BAD ANSWER FILE:: \"" + file.getName() + "\"");
        }
        //out.println("    Finished reading answer file at " + (System.currentTimeMillis() - startTime) + "ms");

        //compare
        //out.println("  Started retrieving edges from input file at " + (System.currentTimeMillis() - startTime) + "ms");
        List<List<Pair<Integer, Double>>> adjList = getEdges(inputFile, file.getName());
       // out.println("    Finished retrieving edges from input file at " + (System.currentTimeMillis() - startTime) + "ms");

        //verify it is a mst (fully connected)
        //out.println("  Starting verifying student answer at " + (System.currentTimeMillis() - startTime) + "ms");
        assertNotNull(adjList, "GRADER ERROR:: BAD INPUT FILE, NO EDGES RETURNED:: \"" + file.getName() + "\"");
        verifyAnswer(adjList, trueWeight, ans);
        //out.println("    Finished verifying student answer and the entire test at " + (System.currentTimeMillis() - startTime) + "ms\n");
    }

    /**
     * Gets the edges from the input file
     * @param inputFile file to get edges from
     * @param fullFileName full file path for error printing
     * @return adjacency list or <code>null</code> if error
     */
    private List<List<Pair<Integer, Double>>> getEdges(String inputFile, String fullFileName) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(inputFile));
            String[] line = bf.readLine().split(" ");
            int numServers = Integer.parseInt(line[0]), numEdges = Integer.parseInt(line[1]);

            List<List<Pair<Integer, Double>>> adjList = new ArrayList<>(numServers);
            for(int i=0; i<numServers; i++) adjList.add(new ArrayList<>());

            //get edges
            int a, b;
            double w;
            for(int i=0; i<numEdges; i++){
                line = bf.readLine().split(" ");
                a = Integer.parseInt(line[0]);
                b = Integer.parseInt(line[1]);
                w = Double.parseDouble(line[2]);
                adjList.get(a).add(new Pair<>(b, w));
                adjList.get(b).add(new Pair<>(a, w));
            }

            return adjList;
        } catch (FileNotFoundException e) {
            fail("GRADER ERROR:: INPUT FILE NOT FOUND:: \"" + fullFileName + "\"");
        } catch (IOException e) {
            fail("GRADER ERROR:: BAD INPUT FILE:: \"" + fullFileName + "\"");
        }
        return null;
    }


    /**
     * Verifies the student answer is correct to 3 decimal places and that the graph is fully connected
     * @param adjList graph adjacency list to get weights from
     * @param trueWeight correct MST weight
     * @param ans answer to check
     */
    private void verifyAnswer(List<List<Pair<Integer, Double>>> adjList, double trueWeight, List<ServerConnection> ans){
        int numServers = adjList.size();
        assertEquals(numServers-1, ans.size(),
                "Incorrect MST size, expected " + (numServers-1) +
                        ", actual is " + ans.size());

        CorrectDisjointSet ds = new CorrectDisjointSet(numServers);
        DoubleAdder totWeight = new DoubleAdder(0);
        for(int i=0; i<ans.size(); i++){
            var edge = ans.get(i);
            //verify indices
            assertTrue(0 <= edge.server1 && edge.server1 < numServers,
                    "Server index out of bounds at index " + i +
                            ", failed check: 0<=" + edge.server1 + "<=" + numServers);
            assertTrue(0 <= edge.server2 && edge.server2 < numServers,
                    "Server index out of bounds at index " + i +
                            ", failed check: 0<=" + edge.server2 + "<=" + numServers);

            //join edge
            ds.union(edge.server1, edge.server2);
            //add weight
            //  this is such an abuse of streams, I love it
            adjList.get(edge.server1)
                    .stream()
                    .filter(p -> p.a == edge.server2)
                    .findFirst()
                    .ifPresentOrElse(p -> totWeight.w += p.b,
                                    () -> fail("No edge from " + edge.server1 + " to " + edge.server2));
        }

        //verify answer is correct to 3 decimal places (via truncation)
        long finalWeight = (long) (totWeight.w * 1000),
             trueFinalWeight = (long) (trueWeight * 1000);
        assertEquals(trueFinalWeight, finalWeight,
                "Weight not correct to 3 decimal places, expected " +
                        trueWeight + ", actual is " + totWeight.w);
    }

    /**
     * Yes, I created a whole class just to make a double "effectively final" for use in a stream() :)
     */
    private class DoubleAdder {
        double w;
        DoubleAdder(double w) { this.w = w; }
    }
}
