public class Main {
    public static void main(String[] args) {

        Graph g = new Graph(5);

        g.addEdge(0, 1, 5);
        g.addEdge(1, 2, 3);
        g.addEdge(0, 3, 2);
        g.addEdge(3, 4, 4);
        g.addEdge(4, 2, 1);

        System.out.println("Before Accident:");
        int[] dist1 = Dijkstra.shortestPath(g, 0);
        System.out.println("Distance to node 2: " + dist1[2]);

        
        g.blockEdge(1, 2);

        System.out.println("\nAfter Accident:");
        int[] dist2 = Dijkstra.shortestPath(g, 0);
        System.out.println("Distance to node 2: " + dist2[2]);

       
        System.out.println("\nCongestion Spread (BFS from node 1):");
        BFS.runBFS(g, 1);
    }
}