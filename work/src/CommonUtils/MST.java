package CommonUtils;

import CommonUtils.UsefulContainers.Edge;
import CommonUtils.UsefulContainers.iPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing Minimum Spanning Tree (MST) utils.  No interface provided because functions are static.
 *
 * <bold>251 students: You may only use java.util.List and java.util.ArrayList from the standard library.
 *   Any other containers used must be ones you created.</bold>
 */
public class MST {
    /**
     * Returns the MST of the given graph, optimized for a dense graph.  Assumes a connected graph.
     *
     * @param weights square matrix representing positive edge weights between every vertex
     * @return MST: list of pairs of indices each indicating an edge between those two indices
     * @throws IllegalArgumentException if weights is not square or edges are not positive
     */
    public static List<iPair> denseMST(double[][] weights) throws IllegalArgumentException {
        //validate weighs matrix (already done)
        int n = weights.length;
        int numVertices = n;
        for(int i=0; i<n; i++){
            if(weights[i].length != n)
                throw new IllegalArgumentException("Weights graph not square in row " +
                        i + ", expected " + n + ", actual is " + weights[i].length);
            for(int j=0; j<n; j++){
                if(weights[i][j] < 0)
                    throw new IllegalArgumentException("Edge weight < 0 (" +
                            weights[i][j] + ") at y, x=" + i + ", " + j);
            }
        }

        List<iPair> MST = new ArrayList<iPair>(); // list to store edges
        int[] visited = new int[numVertices]; // if vertex i is visited visited[i] is 1 else is 0
        MinHeap<Edge> minHeap =  new MinHeap<Edge>();

        // we start prim from vertex 0
        visited[0] = 1;
        // place all incident edges of vertex 0 into min heap
        findAdjacents(weights,minHeap,0,visited);
        while (minHeap.size() != 0) {
            Edge edge = minHeap.removeMin();
            if (visited[edge.b] == 0) {
                MST.add(new iPair(edge.a, edge.b));
                visited[edge.b] = 1;

                findAdjacents(weights,minHeap,edge.b, visited);
            }
        }


        return MST;
    }

    // helper function for prims
    public static void findAdjacents(double[][] weights, MinHeap<Edge> minHeap, int vertex, int[] visited) {
        for (int i = 0; i < weights.length; i++) {
            if(i == vertex) {
                continue;
            }
            else {
                if (visited[i] == 0) {  // check if u has been visited
                    minHeap.add(new Edge(vertex, i, weights[vertex][i])); // add the edge into minheap
                }
            }
        }
    }


    /*
    Kruskal(Graph G):
    Input: G = (V, E), a weighted graph
    Output: MST, the minimum spanning tree

    MST = []                  // To store edges in the MST
    edges = sorted(E, by weight)  // Sort edges by weight
    uf = UnionFind(V)         // Initialize union-find structure

    for each (u, v, weight) in edges:
        if uf.find(u) != uf.find(v):        // Check if adding edge forms a cycle
            MST.append((u, v, weight))     // Add edge to MST
            uf.union(u, v)                 // Union the two components

    return MST
     */


    /**
     * Returns the MST of the given graph, optimized for a sparse graph.  Assumes a connected graph.
     *
     * @param edgeList edge list
     * @param n number of vertices
     * @return MST: list of pairs of indices each indicating an edge between those two indices
     * @throws IllegalArgumentException if edges are not positive
     */
    public static List<iPair> sparseMST(List<Edge> edgeList, int n) throws IllegalArgumentException {
        //validate edge weighs (already done)
        for(var e : edgeList){
            if(e.w < 0)
                throw new IllegalArgumentException("Edge weight < 0 (" +
                        e.w + ") between " + e.a + " and " + e.b);
        }
        List<iPair> MST = new ArrayList<>();
        MinHeap<Edge> edges = new MinHeap<>();
        for (Edge edge : edgeList) {
            edges.add(edge); // sort all edges using heap
        }
        DisjointSet UF = new DisjointSet(n);

        while(edges.size() > 0) {
            Edge edge = edges.removeMin();
            if (UF.find(edge.a) != UF.find(edge.b)) {
                MST.add(new iPair(edge.a, edge.b));
                UF.union(edge.a,edge.b);
            }
        }
        return MST;
    }

}
