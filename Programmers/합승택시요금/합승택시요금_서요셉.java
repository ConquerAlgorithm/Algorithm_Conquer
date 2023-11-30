package Programmers.LV3.합승택시요금;
import java.util.*;

public class 합승택시요금 {

    class Solution {

        static List<List<Edge>> graph;

        public int solution(int n, int s, int a, int b, int[][] fares) {
            int answer = Integer.MAX_VALUE;

            graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }

            for (int i = 0; i < fares.length; i++) {
                int from = fares[i][0];
                int to = fares[i][1];
                int cost = fares[i][2];

                graph.get(from).add(new Edge(to, cost));
                graph.get(to).add(new Edge(from, cost));
            }

            // 어느 한점의 최소 비용이 아닌, 경유지를 생각해야함
            // 따라서 출발지, 도착지 A & B 에서부터 모든 점에 대한 최소 비용을 구하고
            // 해당 지점에서의 합을 구했을 때 최소가 되는 것이 최소비용이다.
            int[] startA = new int[n + 1];
            int[] startB = new int[n + 1];
            int[] start = new int[n + 1];

            Arrays.fill(startA, Integer.MAX_VALUE);
            Arrays.fill(startB, Integer.MAX_VALUE);
            Arrays.fill(start, Integer.MAX_VALUE);

            startA = dijkstra(a, startA);
            startB = dijkstra(b, startB);
            start = dijkstra(s, start);

            for (int i = 1; i <= n; i++) {
                answer = Math.min(answer, startA[i] + startB[i] + start[i]);
            }

            return answer;
        }

        static int[] dijkstra(int start, int[] costs) {
            PriorityQueue<Edge> pq = new PriorityQueue<>();

            pq.add(new Edge(start, 0));
            costs[start] = 0;

            while (!pq.isEmpty()) {
                Edge cur = pq.poll();

                if (cur.cost > costs[cur.idx]) continue;

                List<Edge> edges = graph.get(cur.idx);
                for (Edge edge : edges) {
                    int cost = costs[cur.idx] + edge.cost;

                    if (cost < costs[edge.idx]) {
                        costs[edge.idx] = cost;
                        pq.add(new Edge(edge.idx, cost));
                    }
                }
            }

            return costs;
        }

        static class Edge implements Comparable<Edge> {
            int idx, cost;

            public Edge(int idx, int cost) {
                this.idx = idx;
                this.cost = cost;
            }

            @Override
            public int compareTo(Edge e) {
                return this.cost - e.cost;
            }
        }
    }
}
