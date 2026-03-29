class BFS {

    static void runBFS(Graph g, int start) {
        boolean[] visited = new boolean[g.V];
        Queue<Integer> q = new LinkedList<>();

        q.add(start);
        visited[start] = true;

        int level = 0;

        while (!q.isEmpty()) {
            int size = q.size();

            System.out.print("Level " + level + ": ");

            for (int i = 0; i < size; i++) {
                int node = q.poll();
                System.out.print(node + " ");

                for (Edge e : g.adj.get(node)) {
                    if (!visited[e.to]) {
                        visited[e.to] = true;
                        q.add(e.to);
                    }
                }
            }

            System.out.println();
            level++;
        }
    }
}