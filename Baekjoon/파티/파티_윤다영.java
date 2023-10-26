import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Edge implements Comparable<Edge> {
    public int vex;
    public int cost;

    Edge(int vex, int cost) {
        this.vex = vex;
        this.cost = cost;
    }

    @Override
    public int compareTo(Edge ob) {
        return this.cost - ob.cost;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        ArrayList<ArrayList<Edge>> reverseGraph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
            reverseGraph.add(new ArrayList<>());
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            graph.get(s).add(new Edge(e, cost));
            reverseGraph.get(e).add(new Edge(s, cost));
        }

        int[] dis = dijkstra(X, graph);
        // 다시 돌아가기 위해 방향을 반대로 함
        // ex) 1번에서 2으로 간다는 건 2번에서 1로 돌아간다는 것
        int[] reverseDis = dijkstra(X, reverseGraph);
        int answer = 0;
        N += 1;
        while (N-- > 1) {
            answer = Math.max(answer, dis[N] + reverseDis[N]);
        }
        System.out.println(answer);
    }

    private static int[] dijkstra(int X, List<ArrayList<Edge>> list) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(X, 0));

        boolean[] visited = new boolean[list.size()];
        int[] dis = new int[list.size()];
        Arrays.fill(dis, Integer.MAX_VALUE);
        dis[X] = 0;

        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            int now = edge.vex;

            if (visited[now])
                continue;

            visited[now] = true;

            for (Edge obj : list.get(now)) {
                if (!visited[obj.vex] && dis[obj.vex] > dis[now] + obj.cost) {
                    dis[obj.vex] = dis[now] + obj.cost;
                    pq.add(new Edge(obj.vex, dis[obj.vex]));
                }
            }

        }
        return dis;
    }
}