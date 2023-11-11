package BackJoon.골드.G5.BJ_15486_퇴사2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_15486_퇴사2 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[] T = new int[N + 2];
        int[] P = new int[N + 2];
        int[] DP = new int[N + 2];
        int max = -1;

        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine());

            T[i] = Integer.parseInt(st.nextToken());
            P[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= N + 1; i++) {
            if (max < DP[i]) max = DP[i];

            int next = i + T[i];
            if (next < N + 2) DP[next] = Math.max(DP[next], max + P[i]);
        }

        System.out.println(max);
    }
}
