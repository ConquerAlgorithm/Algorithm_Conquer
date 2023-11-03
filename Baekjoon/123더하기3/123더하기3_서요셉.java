package BackJoon.실버.S2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BJ_15988_123더하기3 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int MOD = 1_000_000_009;

        int T = Integer.parseInt(br.readLine());
        long[] DP = new long[1_000_001];
        DP[1] = 1;
        DP[2] = 2;
        DP[3] = 4;

        for (int i = 4; i <= 1_000_000; i++) {
            DP[i] = (DP[i-1] + DP[i-2] + DP[i-3]) % MOD;
        }

        for (int i = 0; i < T; i++) {
            System.out.println(DP[Integer.parseInt(br.readLine())]);
        }
    }
}
