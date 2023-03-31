/** Starter code for SP5
 *  @author rbk
 */

// change to your netid
package kxs200019;

import kxs200019.Graph;
import kxs200019.Graph.Vertex;
import kxs200019.Graph.Edge;
import kxs200019.Graph.GraphAlgorithm;
import kxs200019.Graph.Factory;
import kxs200019.Graph.Timer;

import java.io.File;
import java.util.*;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
    public static boolean[] visited;
    public static ArrayList<Graph.Vertex> topologicalOrderedList = new ArrayList<>();
    public static boolean isDAG = true;
    public static ArrayDeque<Vertex> stack = new ArrayDeque<>();

    public static class DFSVertex implements Factory {
        int cno;
        public DFSVertex(Vertex u) { }
        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
    }

    public static DFS depthFirstSearch(Graph g) {
        while (!stack.isEmpty()) {
            Vertex vertex = stack.peek();
            if(!visited[vertex.getIndex()]) {
                visited[vertex.getIndex()] = true;
                Iterable<Edge> outEdges = g.outEdges(vertex);
                outEdges.forEach(edge -> {
                    if (!visited[edge.toVertex().getIndex()]) {
                        stack.push(edge.toVertex());
                    } else if (visited[edge.toVertex().getIndex()] && !topologicalOrderedList.contains(edge.toVertex())) {
                        isDAG = false;
                    }
                });
            } else {
                stack.pop();
                topologicalOrderedList.add(vertex);
            }
        }
        return null;
    }

    // Member function to find topological order
    public List<Vertex> topologicalOrder1() {
        visited = new boolean[this.g.size()];
        Vertex[] vertices = this.g.getVertexArray();
        for(Graph.Vertex vertex: vertices) {
            if(!visited[vertex.getIndex()]) {
                stack.add(vertex);
                depthFirstSearch(g);
            }
        }
    return topologicalOrderedList;
    }


    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) throws Exception{
        DFS d = new DFS(g);
        List<Vertex> list = d.topologicalOrder1();
        if(!d.isDAG) {
            throw new Exception("Cycle Found");
        }
        return list;
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
//        String string = "6 6   6 3 2   6 1 3   5 1 5   5 2 4   3 4 1   4 2 7";
//        String string = "7 8   1 2 2   1 3 3   2 4 5  2 6 0  3 4 4   5 1 7   6 7 1   6 5 4";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);


        List<Vertex> topoList = DFS.topologicalOrder1(g);

        // print the vertices in topological order
        // write the code
        for(int i=topoList.size()-1; i>=0; i--) {
            System.out.print(topoList.get(i)+ " ");
        }

    }
}