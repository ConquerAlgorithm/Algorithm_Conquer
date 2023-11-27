package BackJoon.실버.S3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_2606_바이러스_BFS {

    static int N, V, ans;
    static int[][] graph;

    static Queue<Integer> q;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());        // 노드 개수
        V = Integer.parseInt(br.readLine());        // 간선 개수

        graph = new int[N+1][N+1];

        for (int i = 0; i < V; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            graph[x][y] = graph[y][x] = 1;
        }

        bfs();

        System.out.println(ans);
    }

    static void bfs() {
        q = new LinkedList<>();
        visited = new boolean[N+1];

        q.add(1);
        visited[1] = true;

        ans = 0;
        while(!q.isEmpty()) {
            int cur_n = q.poll();

            for (int i = 1; i <= N; i++) {
                if (graph[cur_n][i] == 1 && !visited[i]) {
                    visited[i] = true;
                    q.add(i);
                    ans++;
                }
            }
        }
    }


}
