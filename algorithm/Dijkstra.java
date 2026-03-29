if(edge.blocked) continue;

class Dijkstra {

    static int[] shortestPath(Graph g, int src) {
        int V = g.V;
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

        dist[src] = 0;
        pq.add(new int[]{src, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int d = curr[1];

            if (d > dist[node]) continue;

            for (Edge e : g.adj.get(node)) {
                if (e.blocked) continue; // 🚧 skip blocked roads

                int newDist = d + e.weight;

                if (newDist < dist[e.to]) {
                    dist[e.to] = newDist;
                    pq.add(new int[]{e.to, newDist});
                }
            }
        }

        return dist;
    }
}