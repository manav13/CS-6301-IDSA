package rnb200003;
import rnb200003.Graph.Vertex;
import rnb200003.Graph.Edge;
import rnb200003.Graph.GraphAlgorithm;
import rnb200003.Graph.Factory;
import rnb200003.Graph.Timer;

import java.io.File;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class MinSteps extends Graph.GraphAlgorithm<MinSteps.BFSVertex>{
    public static final int INFINITY = Integer.MAX_VALUE;
    Vertex src;
    int min_steps = Integer.MAX_VALUE;

    public static class BFSVertex implements Factory{
        boolean seen;
        Vertex parent;
        int distance;

        public BFSVertex(Vertex u){
            seen = false;
            parent = null;
            distance = INFINITY;
        }

        public BFSVertex make(Vertex u) {return new BFSVertex(u);}
    }

    public MinSteps(Graph g){
        super(g, new BFSVertex(null));
    }

    public boolean getSeen(Vertex u){
        return get(u).seen;
    }

    public void setSeen(Vertex u, boolean value){
        get(u).seen = value;
    }

    public Vertex getParent(Vertex u){
        return get(u).parent;
    }

    public void setParent(Vertex u, Vertex p){
        get(u).parent = p;
    }

    public int getDistance(Vertex u){
        return get(u).distance;
    }

    public void setDistance(Vertex u, int d){
        get(u).distance = d;
    }

    public void initialize(Vertex src){
        for(Vertex u: g){
            setSeen(u, false);
            setParent(u, null);
            setDistance(u, INFINITY);
        }
        setDistance(src, 0);
    }

    public void setSource(Vertex src){
        this.src = src;
    }

    public Vertex getSource(){
        return this.src;
    }

    void visit(Vertex u, Vertex v){
        setSeen(v, true);
        setParent(v, u);
        setDistance(v, getDistance(u) + 1);
    }

    int getMinSteps(Vertex src1, Vertex src2){
        
        if(src1 == src2){
            return 0;
        }

        setSource(src1);
        initialize(src1);

        Queue<Vertex> q = new LinkedList<>();
        q.add(src1);
        setSeen(src1, true);

        while(!q.isEmpty()){
            Vertex u = q.remove();

            for(Edge e: g.incident(u)){
                Vertex v = e.otherEnd(u);

                if(!getSeen(v)){
                    if(v == src2){
                        int temp_dist = getDistance(u) + 1; 
                        if(temp_dist % 2 == 0){
                            min_steps = Math.min(min_steps, temp_dist);
                            setDistance(v, min_steps);
                        }
                    }

                    else{
                        visit(u,v);
                        q.add(v);
                    }
                }
            }
        }

        return min_steps;
    }

    public static int GetMinSteps(Graph g, Vertex src1, Vertex src2){
        MinSteps st = new MinSteps(g);
        return st.getMinSteps(src1, src2);
    }

    public static int GetMinSteps(Graph g, int src1, int src2){
        return GetMinSteps(g, g.getVertex(src1), g.getVertex(src2));
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        String string = "3 3   1 2 1   2 3 1  1 3 1  1 2";
        Scanner in;
        //Scanner srcin = new Scanner(System.in);
        int src1, src2, total_min_steps;

        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        
        Graph g = Graph.readGraph(in);

        src1 = in.nextInt();
        src2 = in.nextInt();

        total_min_steps = GetMinSteps(g, src1, src2);

        if(total_min_steps == Integer.MAX_VALUE){
            System.out.println("There is no possible ways of coins to be on common vertex");
        }

        else{
            System.out.println("The minimum no of steps required are" +" " + total_min_steps/2);
        }
    }
}
