/* Starter code for PERT algorithm (LP2)
 * @author rbk
 */

// change package to your netid
package kxs200019;

import kxs200019.Graph.Vertex;
import kxs200019.Graph.Edge;
import kxs200019.Graph.GraphAlgorithm;
import kxs200019.Graph.Factory;
import kxs200019.DFS;

import java.util.*;
import java.io.File;
import java.util.Scanner;

public class PERT extends GraphAlgorithm<PERT.PERTVertex> {
	private List<Vertex> topList = new ArrayList<>();
	private static int criticalVtxCount;
    public static class PERTVertex implements Factory {
		private int es, ef, ls, lf, slack, duration;
		public PERTVertex(Vertex u) {
			es = 0;
			ef = 0;
			ls = 0;
			lf = 0;
			slack = 0;
			duration = 0;
			criticalVtxCount = 0;
		}
		public PERTVertex make(Vertex u) {
			return new PERTVertex(u);
		}
    }

    public PERT(Graph g) throws Exception {
		super(g, new PERTVertex(null));
		
		//Adding all edges from s to all vertices and from all vertices to t.
		Vertex s = g.getVertex(1); //Assuming vertex 1 as s.
		Vertex t = g.getVertex(g.n); //Assuming vertex n as t.

		Vertex[] v_arr = g.getVertexArray();

		int edgeCount = g.m;

		for(Vertex v : v_arr){
			if(v.name!=1){
				g.addEdge(s,v,1,++edgeCount);
			}
		}
		for(Vertex v: v_arr){
			if(v.name!=g.n){
				g.addEdge(v,t,1,++edgeCount);
			}
		}

		topList = DFS.topologicalOrder1(g);

    }

    public void setDuration(Vertex u, int d) {
		get(u).duration = d;
    }
    
    public boolean pert() {
		Collections.reverse(topList);
		//System.out.println(topList);
		if(topList.isEmpty()){
			return true;
		}
		else{
			PERTVertex u;
			PERTVertex v;

			for(Vertex vtx : this.topList){
				u = get(vtx);
				u.ef = u.es + u.duration;

				for(Edge e: g.incident(vtx)){
					Vertex oppVtx = e.otherEnd(vtx);
					v = get(oppVtx);
					if(v.es<u.ef){
						v.es = u.ef;
					}
				}
			}

			PERTVertex t = get(g.getVertex(g.n));
			int projectFinishTime = t.ef;
			t.lf = projectFinishTime;
			t.ls = projectFinishTime;

			for(Vertex vtx : this.topList){
				get(vtx).lf = projectFinishTime;
				get(vtx).ls = get(vtx).lf - get(vtx).duration;
			}

			Collections.reverse(topList);
			//System.out.println(topList);
			for(Vertex vtx : this.topList){
				u = get(vtx);
				u.ls = u.lf - u.duration;
				//u.slack = u.lf - u.ef;

				for(Edge e : g.incident(vtx)){
					Vertex oppVtx = e.otherEnd(vtx);
					v = get(oppVtx);

					if(u.lf > v.ls){
						u.lf = v.ls;
						u.ls = u.lf - u.duration;
					}
				}
			}

			for(Vertex vtx : this.topList){
				u = get(vtx);
				u.slack = u.lf - u.ef;

				if(u.slack == 0){
					criticalVtxCount++;
				}
			}

			get(g.getVertex(1)).lf = 0;
			criticalVtxCount-=2;
			return false;
		}
    }
    public int ec(Vertex u) {
		return get(u).ef;
    }

    public int lc(Vertex u) {
		return get(u).lf;
    }

    public int slack(Vertex u) {
		return get(u).slack;
    }

    public int criticalPath() {

		return 0;
    }

    public boolean critical(Vertex u) {
		return ((get(u).lf - get(u).ef) == 0);
    }

    public int numCritical() {
		return criticalVtxCount;
    }

    // setDuration(u, duration[u.getIndex()]);
    public static PERT pert(Graph g, int[] duration) {
	return null;
    }
    
    public static void main(String[] args) throws Exception {
	String graph = "11 12   2 4 1   2 5 1   3 5 1   3 6 1   4 7 1   5 7 1   5 8 1   6 8 1   6 9 1   7 10 1   8 10 1   9 10 1      0 3 2 3 2 1 3 2 4 1 0";
	Scanner in;
	// If there is a command line argument, use it as file from which
	// input is read, otherwise use input from string.
	in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(graph);
	Graph g = Graph.readDirectedGraph(in);
	g.printGraph(false);
	
	PERT p = new PERT(g);
	for(Vertex u: g) {
	    p.setDuration(u, in.nextInt());
	}
	// Run PERT algorithm.  Returns null if g is not a DAG
	if(p.pert()) {
	    System.out.println("Invalid graph: not a DAG");
	} else {
	    System.out.println("Number of critical vertices: " + p.numCritical());
	    System.out.println("u\tEC\tLC\tSlack\tCritical");
	    for(Vertex u: g) {
		System.out.println(u + "\t" + p.ec(u) + "\t" + p.lc(u) + "\t" + p.slack(u) + "\t" + p.critical(u));
	    }
	}
    }
}
