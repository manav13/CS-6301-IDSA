// Starter code for SP9

package mnp200002;

import idsa.Graph.Vertex;
import idsa.Graph.Edge;
import idsa.Graph.GraphAlgorithm;
import idsa.Graph.Factory;
import idsa.Graph.Timer;
import idsa.Graph;

import idsa.BinaryHeap.Index;
import idsa.BinaryHeap.IndexedHeap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.File;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
    String algorithm;
    public long wmst;
    List<Edge> mst;
    
    MST(Graph g) {
		super(g, new MSTVertex((Vertex) null));
    }

    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {

		Boolean seen;
		Integer d;
		MSTVertex parentVertex;
		Vertex vertex;
		Integer index;
		Integer comp;

		// Initialize the MSTVertex with d = infinity, seen = false, parent = null
		MSTVertex(Vertex u) {
			this.d = Integer.MAX_VALUE;
			this.seen = false;
			this.parentVertex = null;
			this.vertex = u;
		}

		MSTVertex(MSTVertex u) {  // for prim2
			this.d = Integer.MAX_VALUE;
			this.seen = false;
			this.parentVertex = null;
			this.vertex = u.vertex;
		}

		public MSTVertex make(Vertex u) { return new MSTVertex(u); }

		public void putIndex(int index) { this.index = index; }

		public int getIndex() { return this.index; }

		public int compareTo(MSTVertex other) {
			if(this.d == other.d) {
				return 0;
			}
			else if(this.d > other.d) {
				return 1;
			}
			return -1;
		}
    }

    public long kruskal() {
		algorithm = "Kruskal";
		Edge[] edgeArray = g.getEdgeArray();
        mst = new LinkedList<>();
        wmst = 0;
        return wmst;
    }

	// Implementation referenced from Graph PPT
	public void label(MSTVertex s, int count, Graph F) {
		LinkedList<MSTVertex> bag = new LinkedList<MSTVertex>();
		bag.add(s);
		while (!bag.isEmpty()) {
			MSTVertex v = bag.remove();
			if (!v.seen) {
				v.seen = true;
				v.comp = count - 1;
				for (Edge e : F.incident(v.vertex)) {
					Vertex w = e.otherEnd(v.vertex);
					bag.add(get(w));
				}
			}
		}
	}

	// Implementation referenced from Graph PPT
	public int countAndLabel(Graph F) {
		int count = 0;
		for (Vertex u : F) {
			get(u).seen = false;
		}
		for (Vertex u : g) {
			if (!get(u).seen) {
				count++;
				label(get(u), count, F);
			}
		}
		return count;
	}

	// Implementation referenced from MST ppt
	public void addSafeEdges(Graph F, int count) {
		Edge safe[] = new Edge[F.size()];
		for (int i = 0; i < count; i++) {
			safe[i] = null;
		}
		for (Edge e : g.getEdgeArray()) {
			MSTVertex u = get(e.fromVertex());
			MSTVertex v = get(e.toVertex());
			if (u.comp != v.comp) {
				if (safe[u.comp] == null || e.compareTo(safe[u.comp]) < 0) {
					safe[u.comp] = e;
				}
				if (safe[v.comp] == null || e.compareTo(safe[v.comp]) < 0) {
					safe[v.comp] = e;
				}
			}
		}
		Set<Edge> visited = new HashSet<>();
		for (int i = 0; i < count; i++) {
			Edge sf = safe[i];
			if (visited.contains(sf))
				continue;
			visited.add(sf);
			wmst += sf.getWeight();
			int m = F.edgeSize();
			F.addEdge(sf.fromVertex(), sf.toVertex(), sf.getWeight(), m++);
		}
	}
	
    public long boruvka() {
		algorithm = "Boruvka";
		wmst = 0;

		// Initialize new Graph F with no edges
		Graph F = new Graph(g.size());

		// Get count from countAndLabel()
		int count = countAndLabel(F);

		// Iterate by adding safe edges and getting count again
		while(count > 1) {
			addSafeEdges(F, count);
			count = countAndLabel(F);
		}

		return wmst;
    }

    public long prim2(Vertex s) {
		algorithm = "indexed heaps";
		mst = new LinkedList<>();
		wmst = 0;
		IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());

		for (Vertex u : g) {
			MSTVertex mstVertex = new MSTVertex(get(u));
		}

		// Initialize source node
		MSTVertex src = get(s);
		src.d = 0;
		src.seen = true;

		for (Vertex u : g) {
			q.add(get(u));
		}

		while(!q.isEmpty()) {
			
			MSTVertex u = q.remove();
			u.seen = true;
			wmst = wmst + u.d;

			for (Edge e : g.incident(u.vertex)) {
				
				MSTVertex v = get(e.otherEnd(u.vertex));
				
				if (!v.seen && e.getWeight() < v.d) {
					v.d = e.getWeight();
					v.parentVertex = u;
					q.decreaseKey(v);
				}
			}
		}

		return wmst;
    }

    public long prim1(Vertex s) {
		algorithm = "PriorityQueue<Edge>";
		mst = new LinkedList<>();
		wmst = 0;
		PriorityQueue<Edge> q = new PriorityQueue<>();
		return wmst;
    }

    public static MST mst(Graph g, Vertex s, int choice) {
		MST m = new MST(g);
		switch(choice) {
		case 0:
			m.boruvka();
			break;
		case 1:
			m.prim1(s);
			break;
		case 2:
			m.prim2(s);
			break;
		case 3:
			m.kruskal();
			break;
		default:
			break;
		}
		return m;
    }

    public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		int choice = 2;  // prim2

		if (args.length == 0 || args[0].equals("-")) {
			in = new Scanner(System.in);
		} else {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		}

		if (args.length > 1) { choice = Integer.parseInt(args[1]); }

		Graph g = Graph.readGraph(in);
			Vertex s = g.getVertex(1);

		Timer timer = new Timer();
		MST m = mst(g, s, choice);
		System.out.println(m.algorithm + "\n" + m.wmst);
		System.out.println(timer.end());
    }
}
