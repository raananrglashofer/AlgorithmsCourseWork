package edu.yu.da;
// https://algs4.cs.princeton.edu/64maxflow/FlowEdge.java.html
public class FlowEdge {
    // to deal with floating-point roundoff errors
    private static final double FLOATING_POINT_EPSILON = 1.0E-10;

    private final String v;             // from
    private final String w;             // to
    private final double capacity;   // capacity
    private double flow;             // flow

    /**
     * Initializes an edge from vertex {@code v} to vertex {@code w} with
     * the given {@code capacity} and zero flow.
     * @param v the tail vertex
     * @param w the head vertex
     * @param capacity the capacity of the edge
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *    is a negative integer
     * @throws IllegalArgumentException if {@code capacity < 0.0}
     */
    public FlowEdge(String v, String w, double capacity) {
        if (v.length() < 1) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        if (w.length() < 1) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        if (!(capacity >= 0.0)) throw new IllegalArgumentException("Edge capacity must be non-negative");
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
    }

    /**
     * Initializes an edge from vertex {@code v} to vertex {@code w} with
     * the given {@code capacity} and {@code flow}.
     * @param v the tail vertex
     * @param w the head vertex
     * @param capacity the capacity of the edge
     * @param flow the flow on the edge
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *    is a negative integer
     * @throws IllegalArgumentException if {@code capacity} is negative
     * @throws IllegalArgumentException unless {@code flow} is between
     *    {@code 0.0} and {@code capacity}.
     */
    public FlowEdge(String v, String w, double capacity, double flow) {
        if (v.length() < 1) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        if (w.length() < 1) throw new IllegalArgumentException("vertex index must be a non-negative integer");
        if (!(capacity >= 0.0))  throw new IllegalArgumentException("edge capacity must be non-negative");
        if (!(flow <= capacity)) throw new IllegalArgumentException("flow exceeds capacity");
        if (!(flow >= 0.0))      throw new IllegalArgumentException("flow must be non-negative");
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = flow;
    }

    /**
     * Initializes a flow edge from another flow edge.
     * @param e the edge to copy
     */
    public FlowEdge(FlowEdge e) {
        this.v = e.v;
        this.w = e.w;
        this.capacity = e.capacity;
        this.flow = e.flow;
    }

    /**
     * Returns the tail vertex of the edge.
     * @return the tail vertex of the edge
     */
    public String from() {
        return v;
    }

    /**
     * Returns the head vertex of the edge.
     * @return the head vertex of the edge
     */
    public String to() {
        return w;
    }

    /**
     * Returns the capacity of the edge.
     * @return the capacity of the edge
     */
    public double capacity() {
        return capacity;
    }

    /**
     * Returns the flow on the edge.
     * @return the flow on the edge
     */
    public double flow() {
        return flow;
    }

    /**
     * Returns the endpoint of the edge that is different from the given vertex
     * (unless the edge represents a self-loop in which case it returns the same vertex).
     * @param vertex one endpoint of the edge
     * @return the endpoint of the edge that is different from the given vertex
     *   (unless the edge represents a self-loop in which case it returns the same vertex)
     * @throws IllegalArgumentException if {@code vertex} is not one of the endpoints
     *   of the edge
     */
    public String other(String vertex) {
        if (vertex.equals(v)) return w;
        else if (vertex.equals(w)) return v;
        else throw new IllegalArgumentException("invalid endpoint");
    }

    /**
     * Returns the residual capacity of the edge in the direction
     *  to the given {@code vertex}.
     * @param vertex one endpoint of the edge
     * @return the residual capacity of the edge in the direction to the given vertex
     *   If {@code vertex} is the tail vertex, the residual capacity equals
     *   {@code capacity() - flow()}; if {@code vertex} is the head vertex, the
     *   residual capacity equals {@code flow()}.
     * @throws IllegalArgumentException if {@code vertex} is not one of the endpoints of the edge
     */
    public double residualCapacityTo(String vertex) {
        if (vertex.equals(v)) return flow;              // backward edge
        else if (vertex.equals(w)) return capacity - flow;   // forward edge
        else throw new IllegalArgumentException("invalid endpoint");
    }

    /**
     * Increases the flow on the edge in the direction to the given vertex.
     * If {@code vertex} is the tail vertex, this increases the flow on the edge by {@code delta};
     * if {@code vertex} is the head vertex, this decreases the flow on the edge by {@code delta}.
     * @param vertex one endpoint of the edge
     * @param delta amount by which to increase flow
     * @throws IllegalArgumentException if {@code vertex} is not one of the endpoints
     *         of the edge
     * @throws IllegalArgumentException if {@code delta} makes the flow
     *         on the edge either negative or larger than its capacity
     * @throws IllegalArgumentException if {@code delta} is {@code NaN}
     */
    public void addResidualFlowTo(String vertex, double delta) {
        if (!(delta >= 0.0)) throw new IllegalArgumentException("Delta must be non-negative");

        if(vertex.equals(v)) flow -= delta;           // backward edge
        else if (vertex.equals(w)) flow += delta;           // forward edge
        else throw new IllegalArgumentException("invalid endpoint");

        // round flow to 0 or capacity if within floating-point precision
        if (Math.abs(flow) <= FLOATING_POINT_EPSILON)
            flow = 0;
        if (Math.abs(flow - capacity) <= FLOATING_POINT_EPSILON)
            flow = capacity;

        if (!(flow >= 0.0))      throw new IllegalArgumentException("Flow is negative");
        if (!(flow <= capacity)) throw new IllegalArgumentException("Flow exceeds capacity");
    }


    /**
     * Returns a string representation of the edge.
     * @return a string representation of the edge
     */
    public String toString() {
        return v + "->" + w + " " + flow + "/" + capacity;
    }
}
