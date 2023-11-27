package BackJoon.실버.S3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_2606_바이러스_DFS {

    static int N, V, ans;
    static int[][] graph;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        V = Integer.parseInt(br.readLine());

        graph = new int[N+1][N+1];
        visited = new boolean[N+1];

        for (int i = 0; i < V; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            graph[x][y] = graph[y][x] = 1;
        }

        dfs(1);

        System.out.println(ans);
    }

    static void dfs(int n) {
        visited[n] = true;

        for (int i = 1; i <= N; i++) {
            if (graph[n][i] == 1 && !visited[i]) {
                dfs(i);
                ans++;
            }
        }
    }
}
