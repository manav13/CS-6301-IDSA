/** Starter code for SP5
 *  @author rbk
 */

// change to your netid
package mnp200002;
import idsa.Graph;
import idsa.Graph.Vertex;
import idsa.Graph.Edge;
import idsa.Graph.GraphAlgorithm;
import idsa.Graph.Factory;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
    public static class DFSVertex implements Factory {
        int cno;
        
        public DFSVertex(Vertex u) {}
        
        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
	    super(g, new DFSVertex(null));
    }

    public static DFS depthFirstSearch(Graph g) {
	    return null;
    }

    // Member function to find topological order
    public List<Vertex> topologicalOrder1() {
	    return null;
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

    public static void dfs(Vertex u , boolean[] visitedArray, Stack<Vertex> numbers, Graph g) {
        visitedArray[u.getName()] = true;

        for(Edge e : g.outEdges(u)) {
            if(!visitedArray[e.toVertex().getName()]) {
                dfs(e.toVertex(), visitedArray, numbers, g);
            }
        }

        numbers.push(u);
    }
    
    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
        if(!isDAG(g)) return null;

        Stack<Vertex> numbers = new Stack<>();
        boolean[] visitedArray = new boolean[g.size()+1];

        for(Vertex u : g) {
            if(!visitedArray[u.getName()]) {
                dfs(u, visitedArray, numbers, g);
            }
        }

        List<Vertex> topoSortNumbers = new ArrayList<>();
        while(!numbers.empty()) {
            topoSortNumbers.add(numbers.pop());
        }

        return topoSortNumbers;
        // DFS d = new DFS(g);
        // return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
	    return null;
    }

    public static boolean isCyclic(Vertex u, boolean[] visitedArray, boolean[] dfsVisited, Graph g) {
        visitedArray[u.getName()] = true;
        dfsVisited[u.getName()] = true;

        for(Edge e : g.outEdges(u)) {
            if(!visitedArray[e.toVertex().getName()]) {
                if(isCyclic(e.toVertex(), visitedArray, dfsVisited, g)) {
                    return true;
                }
            }
            else if(dfsVisited[e.toVertex().getName()]) {
                return true;
            }
        }

        dfsVisited[u.getName()] = false;
        return false;
    }

    public static boolean isDAG(Graph g) {
        if(!g.isDirected()) return false;
        boolean[] visitedArray = new boolean[g.size()+1];
        boolean[] dfsVisited = new boolean[g.size()+1];
        for(Vertex u : g) {
            if(!visitedArray[u.getName()]) {
                if(isCyclic(u, visitedArray, dfsVisited, g)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        // String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1"; // Cyclic 
        // String string = "5 7    1 3 1   1 2 1   3 4 1    4 5 1   2 4 1   4 2 1   5 2 1"; // Cyclic
        // String string = "3 3   1 2 1   2 3 1   3 1 1"; // Cyclic
        // String string = "9 9   1 2 1   2 3 1   3 4 1   4 5 1   3 6 1   6 5 1   7 2 1   7 8 1   8 9 1"; // NonCyclic
        String string = "6 6   6 1 1   5 1 1   5 2 1   6 3 1   3 4 1   4 2 1"; // NonCyclic
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        
        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);
        
        // print the vertices in topological order
        // write the code
        // System.out.println(DFS.isDAG(g));
        
        List<Vertex> topoSort = topologicalOrder1(g);
        if(topoSort == null) {
            System.out.println("\nThe Graph is not a Directed Acyclic Graph! Cannot perform Topological Sorting.");
        }
        else {
            System.out.println("\nTopological Sort: ");
            for(Vertex v : topoSort) {
                System.out.print(v.getName() + " ");
            }
            System.out.println();
        }
    }
}