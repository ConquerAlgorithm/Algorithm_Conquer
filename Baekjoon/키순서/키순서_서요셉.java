package BackJoon.골드.G4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_2458_키_순서 {

    static final int INF = Integer.MAX_VALUE;

    static int N, M, ans;
    static boolean[][] graph;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new boolean[N + 1][N + 1];

        // 연결 여부만 확인하면 된다.
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int small = Integer.parseInt(st.nextToken());
            int big = Integer.parseInt(st.nextToken());

            graph[small][big] = true;
        }

        for (int k = 1; k <= N; k++) {

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (graph[i][k] && graph[k][j]) graph[i][j] = true;
                }
            }
        }

        int[] cnt = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (graph[i][j] || graph[j][i]) cnt[i]++;
            }
        }

        // 모두 연결되어 있다면 등수를 알 수 있다.
        for (int n: cnt) {
            if (n == N - 1) ans++;
        }

        System.out.println(ans);
    }
}
