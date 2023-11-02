package BackJoon.골드.G3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_1238_파티 {
    static final int INF = Integer.MAX_VALUE;
    static ArrayList<ArrayList<Node>> graph, reverseGraph;
    static int N, M, X;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        graph = new ArrayList<>();
        reverseGraph = new ArrayList<>();

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(start).add(new Node(end, cost));
            reverseGraph.get(end).add(new Node(start, cost));
        }

        int[] distToX = dijkstra(graph);
        int[] XToDist = dijkstra(reverseGraph);

        int ans = 0;
        for (int i = 1; i <= N; i++) {
            ans = Math.max(ans, distToX[i] + XToDist[i]);
        }

        System.out.println(ans);
    }

    static int[] dijkstra(ArrayList<ArrayList<Node>> graph) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(X, 0));

        boolean[] visited = new boolean[N + 1];
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);
        dist[X] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int cEnd = cur.end;

            if (!visited[cEnd]) {
                visited[cEnd] = true;

                for (Node node: graph.get(cEnd)) {
                    if (!visited[node.end] && dist[node.end] > dist[cEnd] + node.cost) {
                        dist[node.end] = dist[cEnd] + node.cost;
                        pq.add(new Node(node.end, dist[node.end]));
                    }
                }
            }
        }

        return dist;
    }

    static class Node implements Comparable<Node> {
        int end, cost;

        public Node(int end, int cost) {
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node n) {
            return this.cost - n.cost;
        }
    }
}
