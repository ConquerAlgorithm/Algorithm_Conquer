import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 플로이드 워셜 알고리즘(DP활용)
 * : 모든 점에서 모든 점까지의 최단 거리를 구하는 알고리즘
 *  음의 가중치를 갖는 간선이 있어도 된다. 하지만 합이 음수 가중치를 갖는 사이클이 있어서는 안된다.
 *  // k 중간 노드, i 출발 노드, j 도착 노드
 *  for (int k = 0; k < n; k++) {
 *             for (int i = 0; i < n; i++) {
 *                 for (int j = 0; j < n; j++) {
 *                     if (dist[i][k] != INF && dist[k][j] != INF) {
 *                         dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
 *                     }
 *                 }
 *             }
 *         }
 * */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        boolean[][] check = new boolean[n][n];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            check[a][b] = true;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (check[i][k] && check[k][j]) {
                        check[i][j] = true;
                    }
                }
            }
        }

        int[] cnt = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (check[i][j] || check[j][i]) {
                    cnt[i]++;
                }
            }
        }

        int res = 0;
        for (int num : cnt) {
            if (num == n - 1)
                res++;
        }
        System.out.println(res);

    }
}