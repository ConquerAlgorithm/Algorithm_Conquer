package BackJoon.골드.G5.BJ_11058_크리보드;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class 크리보드_TopDown {

    static long[] DP;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        DP = new long[N + 1];

        System.out.println(solve(N));
    }

    static long solve(int idx) {
        if (idx <= 0) return 0;
        if (idx == 1) return DP[idx] = idx;
        if (DP[idx] > 0) return DP[idx];

        DP[idx] = solve(idx - 1) + 1;
        for (int i = 2; i < 5; i++) {
            DP[idx] = Math.max(DP[idx], solve(idx - (i + 1)) * i);
        }

        return DP[idx];
    }
}
