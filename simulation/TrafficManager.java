package simulation;

import graph.*;
import algorithm.*;

import java.util.*;

public class TrafficManager {

    Graph g;

    public TrafficManager(Graph g) {

        this.g = g;
    }

    public List<Integer> getShortestPath(
            int src,
            int dest
    ) {

        PathResult result =
                Dijkstra.shortestPath(g, src);

        return Dijkstra.buildPath(
                result.parent,
                dest
        );
    }

    // Alternate route generation
    public List<List<Integer>> getAlternatePaths(
            int src,
            int dest
    ) {

        List<List<Integer>> routes =
                new ArrayList<>();

        // MAIN ROUTE
        List<Integer> mainRoute =
                getShortestPath(src, dest);

        routes.add(mainRoute);

        // Temporarily penalize main path
        for (int i = 0;
             i < mainRoute.size() - 1;
             i++) {

            int u = mainRoute.get(i);

            int v = mainRoute.get(i + 1);

            for (Edge e : g.adj.get(u)) {

                if (e.to == v) {

                    e.weight += 20;
                }
            }

            for (Edge e : g.adj.get(v)) {

                if (e.to == u) {

                    e.weight += 20;
                }
            }
        }

        // SECOND ROUTE
        List<Integer> second =
                getShortestPath(src, dest);

        routes.add(second);

        return routes;
    }
}