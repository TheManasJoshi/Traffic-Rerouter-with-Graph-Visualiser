package graph;

public class Edge {

    public int to;
    public int weight;
    public boolean blocked;

    public Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
        this.blocked = false;
    }
}