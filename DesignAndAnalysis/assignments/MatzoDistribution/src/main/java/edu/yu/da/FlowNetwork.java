package edu.yu.da;

// https://algs4.cs.princeton.edu/64maxflow/FlowNetwork.java.html

import java.util.*;
public class FlowNetwork {
    private static final String NEWLINE = System.getProperty("line.separator");
    private int V = 0;
    private int E = 0;
    private Bag<FlowEdge>[] adj;
    private HashMap<String, Integer> vertexIndices = new HashMap<>();
    private HashSet<String> vertices = new HashSet<>();
    private HashSet<FlowEdge> edges = new HashSet<>();

    /**
     * Initializes an empty flow network with {@code V} vertices and 0 edges.
     * @param //V the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    public FlowNetwork(String v, int sourceConstraint) {
        if (v.length() < 0) throw new IllegalArgumentException("Number of vertices in a Graph must be non-negative");
        String vin = v + "_in";
        String vout = v + "_out";
        this.vertexIndices.put(vin, this.V++);
        this.vertices.add(vin);
        this.vertexIndices.put(vout, this.V++);
        this.vertices.add(vout);
        this.adj = (Bag<FlowEdge>[]) new Bag[2];
        FlowEdge sourceFlow = new FlowEdge(vin, vout, sourceConstraint);
        addEdge(sourceFlow);
    }

    public void addVertex(String vertex, int constraint) {
        if (!vertexIndices.containsKey(vertex)) {
            Bag<FlowEdge>[] newAdj = (Bag<FlowEdge>[]) new Bag[V+2];
            for (int v = 0; v < V; v++) {
                newAdj[v] = adj[v];
            }
            newAdj[V] = new Bag<FlowEdge>(); // i don't think this line is neccesary
            adj = newAdj;
            String vin = vertex + "_in";
            String vout = vertex + "_out";
            vertexIndices.put(vin, this.V++);
            this.vertices.add(vin);
            vertexIndices.put(vout, this.V++);
            this.vertices.add(vout);
            FlowEdge vertexEdge = new FlowEdge(vin, vout, constraint);
            addEdge(vertexEdge);
        }
    }

    /**
     * Returns the number of vertices in the edge-weighted graph.
     * @return the number of vertices in the edge-weighted graph
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the edge-weighted graph.
     * @return the number of edges in the edge-weighted graph
     */
    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(String v) {
        if (v.length() < 0 || !vertexIndices.containsKey(v)){
            throw new IllegalArgumentException("Invalid Vertex");
        }
    }

    /**
     * Adds the edge {@code e} to the network.
     * @param e the edge
     * @throws IllegalArgumentException unless endpoints of edge are between
     *         {@code 0} and {@code V-1}
     */
    public void addEdge(FlowEdge e) {
        String v = e.from();
        String w = e.to();
        validateVertex(v);
        validateVertex(w);
        if (adj[vertexIndices.get(v)] == null) {
            adj[vertexIndices.get(v)] = new Bag<FlowEdge>();
        }
        adj[vertexIndices.get(v)].add(e);
        if(adj[vertexIndices.get(w)] == null){
            adj[vertexIndices.get(w)] = new Bag<FlowEdge>();
        }
        adj[vertexIndices.get(w)].add(e); // in place of this code adj[w].add(e) --> come back to this!
        this.edges.add(e);
        E++;
    }

    /**
     * Returns the edges incident on vertex {@code v} (includes both edges pointing to
     * and from {@code v}).
     * @param v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<FlowEdge> adj(String v) {
        validateVertex(v);
        if(adj[vertexIndices.get(v)] == null){
            adj[vertexIndices.get(v)] = new Bag<FlowEdge>();
        }
        return adj[vertexIndices.get(v)];
    }

    // return list of all edges - excludes self loops
    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> list = new Bag<FlowEdge>();
        for (String vertex : this.vertexIndices.keySet()) {
            for (FlowEdge e : adj(vertex)) {
                if (!e.to().equals(vertex)) { // double check this --> not in digraph code
                    list.add(e);
                }
            }
        }
        return list;
    }

    public HashMap<String, Integer> getVertexIndices(){
        return this.vertexIndices;
    }


//    /**
//     * Returns a string representation of the flow network.
//     * This method takes time proportional to <em>E</em> + <em>V</em>.
//     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
//     *    followed by the <em>V</em> adjacency lists
//     */
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        s.append(V + " " + E + NEWLINE);
//        for (int v = 0; v < V; v++) {
//            s.append(v + ":  ");
//            for (FlowEdge e : adj[v]) {
//                if (e.to() != v) s.append(e + "  ");
//            }
//            s.append(NEWLINE);
//        }
//        return s.toString();
//    }
}
