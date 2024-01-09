package Programmers.LV3.산_모양_타일링;

public class 산_모양_타일링 {
    class Solution {

        final int MOD = 10_007;
        public int solution(int n, int[] tops) {
            int answer = 0;

            int[] A = new int[n + 1];
            int[] B = new int[n + 1];

            A[0] = 0;
            B[0] = 1;

            for (int i = 1; i <= n; i++) {
                // 윗 삼각이 붙는 경우
                if (tops[i - 1] == 1) {
                    A[i] = (A[i - 1] + B[i - 1]) % MOD;
                    B[i] = (2 * A[i - 1] + 3 * B[i - 1]) % MOD;
                }
                // 안 붙는 경우
                else {
                    A[i] = (A[i - 1] + B[i - 1]) % MOD;
                    B[i] = (A[i - 1] + 2 * B[i - 1]) % MOD;
                }
            }

            answer = (A[n] + B[n]) % MOD;

            return answer;
        }
    }
}
