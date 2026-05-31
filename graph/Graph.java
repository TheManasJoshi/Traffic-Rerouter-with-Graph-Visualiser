package graph;

import java.util.*;

public class Graph {

    public int V;

    public ArrayList<ArrayList<Edge>> adj;

    public Graph(int V) {

        this.V = V;

        adj = new ArrayList<>();

        for (int i = 0; i < V; i++) {

            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int w) {

        adj.get(u).add(new Edge(v, w));

        adj.get(v).add(new Edge(u, w));
    }

    public void blockEdge(int u, int v) {

        for (Edge e : adj.get(u)) {

            if (e.to == v) {

                e.blocked = true;
            }
        }

        for (Edge e : adj.get(v)) {

            if (e.to == u) {

                e.blocked = true;
            }
        }
    }
}