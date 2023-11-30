package Programmers.LV3.가장먼노드;
import java.util.*;

public class 가장먼노드 {

    class Solution {

        int answer;
        List<List<Integer>> graph;
        boolean[] visited;

        public int solution(int n, int[][] edge) {

            graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }
            visited = new boolean[n + 1];

            for (int i = 0; i < edge.length; i++) {
                int from = edge[i][0];
                int to = edge[i][1];

                graph.get(from).add(to);
                graph.get(to).add(from);
            }

            bfs(n);
            return answer;
        }

        void bfs(int n) {
            Queue<int[]> q = new LinkedList<>();

            q.add(new int[]{1, 0});
            visited[1] = true;
            int maxDist = 0;

            while (!q.isEmpty()) {
                int[] arr = q.poll();

                int node = arr[0];
                int dist = arr[1];

                if (maxDist == dist) answer++;
                else if (maxDist < dist) {
                    maxDist = dist;
                    answer = 1;
                }

                for (int i = 0; i < graph.get(node).size(); i++) {
                    int nextNode = graph.get(node).get(i);

                    if (!visited[nextNode]) {
                        q.add(new int[]{nextNode, dist + 1});
                        visited[nextNode] = true;
                    }
                }
            }
        }
    }
}
