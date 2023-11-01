import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // 날짜별 걸리는 시간, 금액 입력
        int[] time = new int[N + 2];
        int[] pay = new int[N + 2];
        for (int i = 1; i <= N; i++) {
            String[] input = br.readLine().split(" ");

            time[i] = Integer.parseInt(input[0]);
            pay[i] = Integer.parseInt(input[1]);
        }

        int[] dp = new int[N + 2];
        int max = 0;
        for (int i = 1; i <= N + 1; i++) {
            max = Math.max(max, dp[i]);
            int day = i + time[i];
            if (day <= N + 1) {
                dp[day] = Math.max(dp[day], max + pay[i]);
            }
        }

        System.out.println(max);
    }
}

