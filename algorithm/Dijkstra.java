package algorithm;

import graph.*;

import java.util.*;

public class Dijkstra {

    public static PathResult shortestPath(Graph g, int src) {

        int V = g.V;

        int[] dist = new int[V];

        int[] parent = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);

        Arrays.fill(parent, -1);

        PriorityQueue<int[]> pq =
                new PriorityQueue<>((a, b) -> a[1] - b[1]);

        dist[src] = 0;

        pq.add(new int[]{src, 0});

        while (!pq.isEmpty()) {

            int[] curr = pq.poll();

            int node = curr[0];

            int d = curr[1];

            if (d > dist[node]) continue;

            for (Edge e : g.adj.get(node)) {

                if (e.blocked) continue;

                int newDist = d + e.weight;

                if (newDist < dist[e.to]) {

                    dist[e.to] = newDist;

                    parent[e.to] = node;

                    pq.add(new int[]{e.to, newDist});
                }
            }
        }

        return new PathResult(dist, parent);
    }

    public static List<Integer> buildPath(
            int[] parent,
            int destination
    ) {

        List<Integer> path = new ArrayList<>();

        while (destination != -1) {

            path.add(destination);

            destination = parent[destination];
        }

        Collections.reverse(path);

        return path;
    }
}