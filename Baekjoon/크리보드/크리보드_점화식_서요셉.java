package BackJoon.골드.G5.BJ_11058_크리보드;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class 크리보드_점화식 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        long[] DP = new long[N + 1];

        for (int i = 1; i <= N; i++) {
            DP[i] = DP[i - 1] + 1;

            if (i > 6) {
                for (int j = 2; j < 5; j++) {
                    DP[i] = Math.max(DP[i], DP[i - (j + 1)] * j);
                }
            }
        }

        System.out.println(DP[N]);
    }
}
