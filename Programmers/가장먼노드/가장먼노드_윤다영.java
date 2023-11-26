import java.util.*;

class Solution {
    class Graph {
        List<List<Integer>> adj;
        int v;

        public Graph(int v) {
            this.v = v;
            adj = new ArrayList<>();
            for(int i = 0; i <= v; i++) {
                adj.add(new ArrayList<>());
            }
        }

        public void add(int start, int end) {
            adj.get(start).add(end);
            adj.get(end).add(start);
        }
    }


    public int solution(int n, int[][] edge) {
        Graph graph = new Graph(n);
        int answer = 0;

        for(int[] arr : edge) {
            graph.add(arr[0], arr[1]);
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        boolean[] visited = new boolean[n + 1];
        visited[1] = true;
        int[] dist = new int[n + 1];
        while(!q.isEmpty()) {
            int temp = q.poll();
            for(int num : graph.adj.get(temp)) {
                if(visited[num]) continue;
                q.add(num);
                visited[num] = true;
                dist[num] = dist[temp] + 1;
            }
        }
        Arrays.sort(dist);
        for(int i = n; i > 0 && dist[n] == dist[i]; i--) {
            //System.out.println(dist[i] + " " + i);
            answer++;
        }
        return answer;
    }

}