package BackendManagers;

import BackendManagers.Interfaces.ServerConnectionsUtilInterface;
import BackendManagers.Interfaces.WorldBuildingUtilInterface;
import CommonUtils.UsefulContainers.iPair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains helpful functions for dealing with our world building. Will not be integrated with
 *   production classes, as it is a tool for helping us with content creation.
 *
 * <bold>251 students: you may use any of the data structures you have previously created, but may not use
 *   any Java util library except List/ArrayList and java.util.stream.* .</bold>
 */
public class WorldBuildingUtil implements WorldBuildingUtilInterface {
    /**
     * Selects roads per the specifications (minimum cost to connect all cities).
     *
     * @param filename file to read input from
     * @return list of roads, per the specifications
     */
    @Override
    public List<CityEdge> getMinimumConnectingRoads(String filename) {
        try {
            List<CityEdge> MST = new ArrayList<>();
            BufferedReader bf = new BufferedReader(new FileReader(filename));
            int numCities;
            numCities = Integer.parseInt(bf.readLine());
            //System.out.println("there are " + numCities + "cities");

            double[][] weights = new double[numCities][numCities];
            int[][] coords = new int[numCities][2];    // coords[0] = x coords[1] = y

            //fill coords
            for (int i = 0; i < numCities; i++) {
                String[] line = bf.readLine().split(" ");
                coords[i][0] = Integer.parseInt(line[0]);
                coords[i][1] = Integer.parseInt(line[1]);
                //System.out.println("reading in coords" + coords[i][0] + " , " + coords[i][1] );
            }

            //fill weights
            for (int i = 0; i < numCities; i++) {
                for (int j = i; j < numCities; j++) {
                    double distance;
                    int delx = (coords[i][0] - coords[j][0]) * (coords[i][0] - coords[j][0]); // change in x squared
                    int dely = (coords[i][1] - coords[j][1]) * (coords[i][1] - coords[j][1]); // change in y squared

                    // int delx = (coords[i][0] - coords[j][0])^2;   // change in x squared
                    //int dely = (coords[i][1] - coords[j][1])^2;   // change in y squared
                    distance = Math.sqrt(delx + dely);
                    weights[i][j] = weights[j][i] = distance; // Fill both halves for symmetry
                    //System.out.println("The distance between " + i + " and " + j + " is" + distance);
                }
            }

            List<iPair> denseMST = CommonUtils.MST.denseMST(weights);

            //System.out.println(denseMST);
            for (iPair edge : denseMST) {
                iPair city1 = new iPair(coords[edge.a][0], coords[edge.a][1]);
                iPair city2 = new iPair(coords[edge.b][0], coords[edge.b][1]);
                MST.add(new CityEdge(city1, city2));
            }

            return MST;
            //todo
        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {
        WorldBuildingUtil worldBuildingUtil = new WorldBuildingUtil();
        worldBuildingUtil.getMinimumConnectingRoads("work/test/BackendManagerTest/connectingCitiesFiles/sample/sample.in");
    }
}
