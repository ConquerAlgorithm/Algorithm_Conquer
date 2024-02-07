package BackJoon.골드.G4.BJ_10942_팰린드롬;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static int[] arr;
    static boolean[][] DP;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        arr = new int[N + 1];
        DP = new boolean[N + 1][N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        progress();

        M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int S = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());

            if (DP[S][E]) sb.append("1\n");
            else sb.append("0\n");
        }

        System.out.println(sb);
    }

    static void progress() {
        for (int i = 1; i <= N; i++) {
            DP[i][i] = true;
        }

        for (int i = 1; i <= N - 1; i++) {
            if (arr[i] == arr[i + 1]) DP[i][i + 1] = true;
        }

        for (int i = 2; i < N; i++) {
            for (int j = 1; j <= N - i; j++) {
                if (arr[j] == arr[j + i] && DP[j + 1][j + i - 1]) {
                    DP[j][j + i] = true;
                }
            }
        }
    }
}
