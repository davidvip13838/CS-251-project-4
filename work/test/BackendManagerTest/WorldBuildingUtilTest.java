package BackendManagerTest;

import BackendManagers.Interfaces.WorldBuildingUtilInterface;
import BackendManagers.Interfaces.WorldBuildingUtilInterface.CityEdge;
import BackendManagers.WorldBuildingUtil;
import CommonUtils.UsefulContainers.iPair;
import CommonUtilsTest.UsefulObjects.CorrectDisjointSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Tests {@link BackendManagers.WorldBuildingUtil}.
 * @author 
 */
public class WorldBuildingUtilTest {
    final static String prefix = "work/test/BackendManagerTest/connectingCitiesFiles/";
    final static File[] folderPaths = {new File(prefix + "sample"),
            new File(prefix + "manual"),
            new File(prefix + "generated")
    };
    final static String inputSuffix = "in", ansSuffix = "out";

    /**
     * All done in one instance of the manager
     */
    WorldBuildingUtilInterface manager = new WorldBuildingUtil();


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
    @DisplayName("File-based tests for Story 6")
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
        List<CityEdge> ans = null;
        try {
            ans = manager.getMinimumConnectingRoads(inputFile);
        } catch(Exception e){
            e.printStackTrace();
            fail("Error calling getMinimumConnectingRoads(\"" + file.getName() + "\": " + e.getMessage());
        }
        //out.println("  Student response ended at " + (System.currentTimeMillis() - startTime) + "ms");

        //compare to answer
        //read in answer file and edges
        //out.println("  Reading answer file at " + (System.currentTimeMillis() - startTime) + "ms");
        double trueWeight = -1;
        try {
            BufferedReader bf = new BufferedReader(new FileReader(ansFile));
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
        Map<iPair, Integer> cityToIdx = new HashMap<>();
        double[][] adjMatrix = getAdjMatrix(inputFile, file.getName(), cityToIdx);
        //out.println("    Finished retrieving edges from input file at " + (System.currentTimeMillis() - startTime) + "ms");

        //verify it is a mst (fully connected)
        //out.println("  Starting verifying student answer at " + (System.currentTimeMillis() - startTime) + "ms");
        assertNotNull(adjMatrix, "GRADER ERROR:: BAD INPUT FILE, NO EDGES RETURNED:: \"" + file.getName() + "\"");
        verifyAnswer(adjMatrix, cityToIdx, trueWeight, ans);
        //out.println("    Finished verifying student answer and the entire test at " + (System.currentTimeMillis() - startTime) + "ms\n");
    }


    /**
     * Gets the edges from the input file
     * @param inputFile file to get edges from
     * @param fullFileName full file path for error printing
     * @return adjacency list or <code>null</code> if error
     */
    private double[][] getAdjMatrix(String inputFile, String fullFileName, Map<iPair, Integer> cityToIdx) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(inputFile));
            int numCities = Integer.parseInt(bf.readLine().split(" ")[0]);

            //generate edges
            List<iPair> cities = new ArrayList<>(numCities);
            String[] line;
            for(int i=0; i<numCities; i++){
                line = bf.readLine().split(" ");
                var temp = new iPair(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
                cities.add(temp);
                cityToIdx.put(temp, i);
            }

            //generate adjacency matrix
            double[][] adjMatrix = new double[numCities][numCities];
            for (int i = 0; i < cities.size(); i++) {
                iPair c = cities.get(i);
                for (int j = 0; j < cities.size(); j++) {
                    if (i != j) {
                        adjMatrix[i][j] = adjMatrix[j][i] = Math.hypot(c.a - cities.get(j).a, c.b - cities.get(j).b);
                    }
                }
            }

            return adjMatrix;
        } catch (FileNotFoundException e) {
            fail("GRADER ERROR:: INPUT FILE NOT FOUND:: \"" + fullFileName + "\"");
        } catch (IOException e) {
            fail("GRADER ERROR:: BAD INPUT FILE:: \"" + fullFileName + "\"");
        }
        return null;
    }


    /**
     * Verifies the student answer is correct to 3 decimal places and that the graph is fully connected
     * @param adjMatrix graph adjacency list to get weights from
     * @param cityToIdx map from city coordinates to index in adjacency matrix
     * @param trueWeight correct MST weight
     * @param ans answer to check
     */
    private void verifyAnswer(double[][] adjMatrix, Map<iPair, Integer> cityToIdx, double trueWeight, List<CityEdge> ans){//, boolean writeAnswer, String ansFile) {
        int numCities = adjMatrix.length;
        assertEquals(numCities-1, ans.size(),
                "Incorrect MST size, expected " + (numCities-1) +
                        ", actual is " + ans.size());

        CorrectDisjointSet ds = new CorrectDisjointSet(numCities);
        double totWeight = 0;
        for (CityEdge edge : ans) {
            var idx1 = cityToIdx.get(edge.city1);
            var idx2 = cityToIdx.get(edge.city2);
            //verify indices
            assertNotNull(idx1, "Bad edge: No edge from " + edge.city1.toString() + " to " + edge.city2.toString());

            //join edge
            ds.union(idx1, idx2);
            //add weight
            totWeight += adjMatrix[idx1][idx2];
        }

        //verify answer is correct to 3 decimal places (via truncation)
        long finalWeight = (long) (totWeight * 1000),
             trueFinalWeight = (long) (trueWeight * 1000);
        assertEquals(trueFinalWeight, finalWeight,
                "Weight not correct to 3 decimal places, expected " +
                        trueWeight + ", actual is " + totWeight);
    }
}
