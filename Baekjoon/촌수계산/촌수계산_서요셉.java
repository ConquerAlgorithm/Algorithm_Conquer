package BackJoon.실버.S2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_2644_촌수계산 {

    static int N, M, targetNode1, targetNode2, ans;
    static int[][] graph;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        graph = new int[N + 1][N + 1];
        visited = new boolean[N + 1];
        ans = Integer.MAX_VALUE;

        st = new StringTokenizer(br.readLine());
        targetNode1 = Integer.parseInt(st.nextToken());
        targetNode2 = Integer.parseInt(st.nextToken());

        M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            graph[x][y] = graph[y][x] = 1;
        }

        DFS(targetNode1, 0);

        ans = (ans != Integer.MAX_VALUE) ? ans : -1;
        System.out.println(ans);
    }

    static void DFS(int start, int dist) {
        visited[start] = true;

        if (start == targetNode2) {
            ans = dist;
            return;
        }

        for (int i = 1; i <= N; i++) {
            if (graph[start][i] == 1 && !visited[i]) {
                DFS(i, dist + 1);
            }
        }

    }

    static void printBoard() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
