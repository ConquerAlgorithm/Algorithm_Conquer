package Programmers.LV3;

public class 연속_펄스_부분_수열의_합 {
    class Solution {
        public long solution(int[] sequence) {
            long answer = 0;

            int N = sequence.length;
            int[] a = new int[N];
            int[] b = new int[N];
            int mul = 1;

            for (int i = 0; i < N; i++) {
                a[i] = sequence[i] * mul;
                mul *= -1;
                b[i] = sequence[i] * mul;
            }

            long[] aDP = new long[N];
            long[] bDP = new long[N];

            aDP[0] = a[0];
            bDP[0] = b[0];
            answer = Math.max(aDP[0], bDP[0]);

            for (int i = 1; i < N; i++) {
                aDP[i] = Math.max(a[i], a[i] + aDP[i - 1]);
                bDP[i] = Math.max(b[i], b[i] + bDP[i - 1]);

                long max = Math.max(aDP[i], bDP[i]);
                answer = Math.max(answer, max);
            }

            return answer;
        }
    }
}
