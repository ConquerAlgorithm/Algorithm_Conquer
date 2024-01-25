package BackJoon.골드.G5.BJ_2293_동전1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int[] arr = new int[N + 1];
        int[] DP = new int[K + 1];

        DP[0] = 1;

        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
            for (int j = arr[i]; j <= K; j++) {
                DP[j] += DP[j - arr[i]];
            }
        }

        System.out.println(DP[K]);
    }
}
