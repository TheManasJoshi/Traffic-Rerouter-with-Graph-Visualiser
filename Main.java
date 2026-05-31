import graph.*;
import simulation.*;

public class Main {

    public static void main(String[] args) {

        Graph g = new Graph(16);

        g.addEdge(0, 1, 4);
        g.addEdge(1, 2, 5);
        g.addEdge(2, 3, 3);
        g.addEdge(3, 4, 6);

        g.addEdge(0, 5, 7);
        g.addEdge(5, 6, 2);
        g.addEdge(6, 7, 4);
        g.addEdge(7, 8, 3);
        g.addEdge(8, 9, 5);

        g.addEdge(5, 10, 6);
        g.addEdge(10, 11, 3);
        g.addEdge(11, 12, 4);
        g.addEdge(12, 13, 5);

        g.addEdge(1, 15, 4);
        g.addEdge(15, 14, 3);
        g.addEdge(14, 3, 2);

        g.addEdge(6, 11, 4);
        g.addEdge(7, 12, 5);
        g.addEdge(2, 7, 2);
        g.addEdge(3, 8, 3);
        g.addEdge(8, 13, 4);

        GraphVisualizer.showGraph(g);
    }
}