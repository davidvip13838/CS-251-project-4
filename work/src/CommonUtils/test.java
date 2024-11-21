package CommonUtils;

import CommonUtils.UsefulContainers.Edge;
import CommonUtils.UsefulContainers.iPair;

import java.util.ArrayList;
import java.util.List;

import static CommonUtils.MST.denseMST;
import static CommonUtils.MST.sparseMST;

public class test {

    public static void main(String[] args) {
        // Test Case 1: Simple 3-vertex dense graph
        double[][] weights1 = {
                {0, 1, 3},
                {1, 0, 2},
                {3, 2, 0}
        };
        System.out.println("Test Case 1 - Dense Graph:");
        try {
            List<iPair> mst1 = denseMST(weights1);
            System.out.println("MST: " + mst1);
            // Expected: [(0, 1), (1, 2)]
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 2: Single vertex graph
        double[][] weights2 = {
                {0}
        };
        System.out.println("\nTest Case 2 - Single Vertex:");
        try {
            List<iPair> mst2 = denseMST(weights2);
            System.out.println("MST: " + mst2);
            // Expected: []
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 3: Disconnected graph
        double[][] weights3 = {
                {0, 1, 0},
                {1, 0, 0},
                {0, 0, 0}
        };
        System.out.println("\nTest Case 3 - Disconnected Graph:");
        try {
            List<iPair> mst3 = denseMST(weights3);
            System.out.println("MST: " + mst3);
            // Expected: Partial MST or an error (if disconnected graphs are unsupported)
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 4: Sparse graph with 4 vertices and 3 edges
        ArrayList<Edge> edgeList4 = new ArrayList<>();
        edgeList4.add(new Edge(0, 1, 1));
        edgeList4.add(new Edge(1, 2, 2));
        edgeList4.add(new Edge(2, 3, 3));
        System.out.println("\nTest Case 4 - Sparse Graph:");
        try {
            List<iPair> mst4 = sparseMST(edgeList4, 4);
            System.out.println("MST: " + mst4);
            // Expected: [(0, 1), (1, 2), (2, 3)]
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 5: Graph with negative weights
        List<Edge> edgeList5 = new ArrayList<>();
        edgeList5.add(new Edge(0, 1, -1));
        edgeList5.add(new Edge(1, 2, 2));
        edgeList5.add(new Edge(2, 3, 3));
        System.out.println("\nTest Case 5 - Negative Weights:");
        try {
            List<iPair> mst5 = sparseMST(edgeList5, 4);
            System.out.println("MST: " + mst5);
            // Expected: IllegalArgumentException
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 6: Fully connected dense graph with 4 vertices
        double[][] weights6 = {
                {0, 1, 2, 3},
                {1, 0, 4, 5},
                {2, 4, 0, 6},
                {3, 5, 6, 0}
        };
        System.out.println("\nTest Case 6 - Fully Connected Dense Graph:");
        try {
            List<iPair> mst6 = denseMST(weights6);
            System.out.println("MST: " + mst6);
            // Expected: [(0, 1), (0, 2), (0, 3)] or equivalent
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test Case 7: Large sparse graph
        List<Edge> edgeList7 = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            edgeList7.add(new Edge(i, (i + 1) % 1000, i + 1));
        }
        System.out.println("\nTest Case 7 - Large Sparse Graph:");
        try {
            List<iPair> mst7 = sparseMST(edgeList7, 1000);
            System.out.println("MST size: " + mst7.size());
            // Expected: 999 edges
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
