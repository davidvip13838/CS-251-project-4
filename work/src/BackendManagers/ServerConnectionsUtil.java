package BackendManagers;

import BackendManagers.Interfaces.ServerConnectionsUtilInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import BackendManagers.Interfaces.WorldBuildingUtilInterface;
import CommonUtils.MST;
import CommonUtils.UsefulContainers.Edge;
import CommonUtils.UsefulContainers.iPair;

/**
 * Contains helpful functions for dealing with our server connections. Will not be integrated with
 *   production classes, as it is a tool for helping us with backend setup.
 *
 * <bold>251 students: you may use any of the data structures you have previously created, but may not use
 *   any Java util library except List/ArrayList and java.util.stream.* .</bold>
 */
public class ServerConnectionsUtil implements ServerConnectionsUtilInterface {
    /**
     * Selects server connections per the specifications (minimum cost to connect all servers).  Assumes
     * all servers can be connected.
     *
     * @param filename file to read input from
     * @return list of server connections, per the specifications
     */
    @Override
    public List<ServerConnection> getMinimumServerConnections(String filename) {
        try {
            List<ServerConnection> MST = new ArrayList<>();
            BufferedReader bf = new BufferedReader(new FileReader(filename));
            int numVertices;
            int numEdges;
            String[] line = bf.readLine().split(" ");
            numVertices = Integer.parseInt(line[0]);
            numEdges = Integer.parseInt(line[1]);

            // read in edges
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < numEdges; i++) {
                String[] edgeInfo = bf.readLine().split(" ");
                int server1 = Integer.parseInt(edgeInfo[0]);
                int server2 = Integer.parseInt(edgeInfo[1]);
                double weight = Double.parseDouble(edgeInfo[2]);
                edges.add(new Edge(server1,server2,weight));
            }

            //run MST sparse
            List<iPair> sparseMST = CommonUtils.MST.sparseMST(edges,numVertices);

            // transfer to serverconnections
            for (iPair edge : sparseMST) {
                MST.add(new ServerConnection(edge.a,edge.b));
            }

            return MST;

        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
