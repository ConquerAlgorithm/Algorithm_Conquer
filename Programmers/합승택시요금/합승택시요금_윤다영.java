import java.util.*;

class Solution {
    class Node implements Comparable<Node> {
        int v;
        int cost;

        Node(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node n) {
            return this.cost - n.cost;
        }
    }

    List<List<Node>> graph;
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = Integer.MAX_VALUE;
        graph = new ArrayList<>();
        for(int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for(int[] fare : fares) {
            graph.get(fare[0]).add(new Node(fare[1], fare[2]));
            graph.get(fare[1]).add(new Node(fare[0], fare[2]));
        }

        int[] startA = dijkstra(a,n);
        int[] startB = dijkstra(b,n);
        int[] start = dijkstra(s,n);

        // s -> v 까지의 합승
        // s -> v 거리 + s -> a 거리 + s -> b 거리
        for(int i=1; i<=n; i++){
            answer = Math.min(answer, start[i] + startA[i] + startB[i]);
        }

        return answer;
    }

    public int[] dijkstra(int s, int n) {
        int[] dist = new int[n+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<Node> q = new PriorityQueue<>();
        q.add(new Node(s, 0));
        dist[s] = 0;

        while(!q.isEmpty()) {
            Node temp = q.poll();
            for(Node node : graph.get(temp.v)) {
                int newDist = temp.cost + node.cost;
                if(dist[node.v] > newDist) {
                    dist[node.v] = newDist;
                    q.add(new Node(node.v, newDist));
                }
            }

        }
        return dist;
    }
}