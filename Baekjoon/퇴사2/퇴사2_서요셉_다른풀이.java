package BackJoon.골드.G5.BJ_15486_퇴사2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_15486_퇴사2_풀이 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int MAX = 1_500_000;
        int[] T = new int[N + MAX];
        int[] P = new int[N + MAX];
        int[] DP = new int[N + MAX];
        int max = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            T[i] = Integer.parseInt(st.nextToken());
            P[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i <= N; i++) {
            DP[i] = Math.max(DP[i], max);

            DP[T[i] + i] = Math.max(DP[T[i] + i], P[i] + DP[i]);

            max = Math.max(max, DP[i]);
        }

        System.out.println(max);
    }
}
