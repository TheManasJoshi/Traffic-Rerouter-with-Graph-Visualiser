class Edge {
    int to;
    int weight;
    boolean blocked;

    Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
        this.blocked = false;
    }
}