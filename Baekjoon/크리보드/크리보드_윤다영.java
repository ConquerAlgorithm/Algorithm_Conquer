import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// https://tussle.tistory.com/810
// 난 dp가 너무 어렵다
class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long[] dp = new long[N+1];

        for(int i = 1; i <= N; i++) {
            dp[i] = dp[i-1] + 1;
            if(i > 6) {
                for(int j = 3; j <= 5; j++) {
                    dp[i] = Math.max(dp[i], dp[i-j] * (j-1));
                }
            }
        }
        System.out.print(dp[N]);
    }
}