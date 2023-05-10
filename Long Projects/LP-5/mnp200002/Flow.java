// Starter code for max flow

/*
   LP Group 2: 

   Manav Prajapati (mnp200002)
   Rahul Bosamia (rnb200003)
   Mayank Goyani (mxg200078)
   Kalyan Kumar (axs200019)

 */

package mnp200002;

import idsa.Graph;
import idsa.Graph.*;
import java.util.HashMap;
import java.util.Set;
import java.util.*;

public class Flow {

   Graph g;
   Vertex source, sink;
   HashMap<Edge, Integer> edgeCapacity, edgeFlow;
   int n;
   int height[], excessFlow[];
   List<Vertex> activeNodes;

   public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
      this.g = g;
      this.source = s;
      this.sink = t;
      this.edgeCapacity = capacity;
      this.n = g.size();
      this.height = new int[n];
      this.excessFlow = new int[n];
      this.activeNodes = new LinkedList<>();
      this.edgeFlow = new HashMap<>();
   }


   public void initialize() {

      for(Edge e : g.getEdgeArray()) {
         edgeFlow.put(e, 0);
      }

      for(Vertex u : g.getVertexArray()) {
         if(!u.equals(source) && !u.equals(sink)) {
            activeNodes.add(u);
         }

         excessFlow[u.getIndex()] = 0;
         height[u.getIndex()] = 0;
      }

      int sourceIndex = source.getIndex();
      height[sourceIndex] = n;

      for(Edge e : g.outEdges(source)) {
         edgeFlow.put(e, capacity(e));
         excessFlow[sourceIndex] -= capacity(e);
         excessFlow[e.otherEnd(source).getIndex()] += capacity(e);
      }
   }

   public boolean flowResidualGraph(Vertex u, Edge e) {
      if(e.fromVertex().equals(u))
         return flow(e) < capacity(e);
      else
         return flow(e) > 0;
   }

   public void relabel(Vertex u) {
      int minHeight = 3*n;

      for(Edge e : g.inEdges(u)) {
         Vertex v = e.fromVertex();
         if(flowResidualGraph(u, e) && height[v.getIndex()] < minHeight) {
            minHeight = height[v.getIndex()];
         }
      }

      for(Edge e : g.outEdges(u)) {
         Vertex v = e.toVertex();
         if(flowResidualGraph(u, e) && height[v.getIndex()] < minHeight) {
            minHeight = height[v.getIndex()];
         }
      }

      height[u.getIndex()] = minHeight+1;
   }

   public void discharge(Vertex u) {
      while(excessFlow[u.getIndex()] > 0) {

         for(Edge e : g.inEdges(u)) {
            Vertex v = e.fromVertex();
            if(flowResidualGraph(u, e) && height[u.getIndex()] == height[v.getIndex()]+1) {
               // add(e,u,v)

               int delta = 0;
               if(e.fromVertex().equals(u)) {
                  int residual = capacity(e) - flow(e);
                  delta = Math.min(excessFlow[u.getIndex()], residual);
                  edgeFlow.put(e, flow(e) + delta);
               }
               else {
                  delta = Math.min(excessFlow[u.getIndex()], flow(e));
                  edgeFlow.put(e, flow(e) - delta);
               }

               excessFlow[v.getIndex()] += delta;
               excessFlow[u.getIndex()] -= delta;

               // end_add

               if(excessFlow[u.getIndex()] == 0)
                  return;
               
            }
         }

         for(Edge e : g.outEdges(u)) {
            Vertex v = e.otherEnd(u);
            if(flowResidualGraph(u, e) && height[u.getIndex()] == height[v.getIndex()] + 1) {
               int delta = 0;
               if(e.fromVertex().equals(u)) {
                  int residual = capacity(e) - flow(e);
                  delta = Math.min(excessFlow[u.getIndex()], residual);
                  edgeFlow.put(e, flow(e) + delta);
               }
               else {
                  delta = Math.min(excessFlow[u.getIndex()], flow(e));
                  edgeFlow.put(e, flow(e) - delta);
               }

               excessFlow[v.getIndex()] += delta;
               excessFlow[u.getIndex()] -= delta;

               if(excessFlow[u.getIndex()] == 0)
                  return;
            }
         }

         relabel(u);
      }
   }

   // Return max flow found. Implement either FIFO or Priority based on height.
   public int preflowPush() {

      initialize();
      boolean completed = false;

      while(!completed) {
         completed = true;

         Iterator<Vertex> v = activeNodes.iterator();
         while(v.hasNext()) {
            Vertex node = v.next();
            if(excessFlow[node.getIndex()] == 0) {
               continue;
            }

            int prevHeight = height[node.getIndex()];
            discharge(node);
            if(prevHeight != height[node.getIndex()]) {
               completed = false;
               v.remove();
               activeNodes.add(0, node);
               break;
            }
         }
      }

      return excessFlow[sink.getIndex()];
   }

   // flow going through edge e
   public int flow(Edge e) {
      return edgeFlow.containsKey(e) ? edgeFlow.get(e) : 0;
   }

   // capacity of edge e
   public int capacity(Edge e) {
      return edgeCapacity.containsKey(e) ? edgeCapacity.get(e): 0;
   }

   /* After maxflow has been computed, this method can be called to
      get the "S"-side of the min-cut found by the algorithm
   */
   public Set<Vertex> minCutS() {
      Set<Vertex> minCut = new HashSet<>();
      Queue<Vertex> q = new LinkedList<Vertex>();

      q.add(source);
      while(!q.isEmpty()) {
         Vertex v = q.poll();
         minCut.add(v);
         for(Edge e : g.outEdges(v)) {
            if(edgeFlow.get(e) < edgeCapacity.get(e)) {
               q.add(e.otherEnd(v));
            }
         }
      }

      return minCut;
   }

   /* After maxflow has been computed, this method can be called to
      get the "T"-side of the min-cut found by the algorithm
   */
   public Set<Vertex> minCutT() {
      Set<Vertex> sourceCut = minCutS();
      Set<Vertex> sinkCut = new HashSet<>();

      for(Vertex v : g) {
         if(!sourceCut.contains(v)) {
            sinkCut.add(v);
         }
      }
      return sinkCut;
   }
}
