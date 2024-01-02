package Programmers.LV3.에어컨;

class Solution {

    final int MAX_TEMP = 50;
    final int MAX_V = 1_000 * 100;

    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        temperature += 10;
        t1 += 10;
        t2 += 10;

        // i분 j도에 드는 최소 전력 기록
        int[][] DP = new int[onboard.length][MAX_TEMP + 1];
        for (int i = 0; i < onboard.length; i++) {
            for (int j = 0; j < MAX_TEMP + 1; j++) {
                DP[i][j] = MAX_V;
            }
        }

        DP[0][temperature] = 0;

        int flag = (temperature > t2) ? -1 : 1;

        for (int i = 1; i < onboard.length; i++) {
            for (int j = 0; j < MAX_TEMP + 1; j++) {
                int minV = MAX_V;

                if ((onboard[i] == 1 && t1 <= j && j <= t2) || onboard[i] == 0) {
                    if (0 <= j + flag && j + flag <= MAX_TEMP) {
                        minV = Math.min(minV, DP[i - 1][j + flag]);
                    }

                    if (j == temperature) {
                        minV = Math.min(minV, DP[i - 1][j]);
                    }

                    if (0 <= j - flag && j - flag <= MAX_TEMP) {
                        minV = Math.min(minV, DP[i - 1][j - flag] + a);
                    }

                    if (t1 <= j && j <= t2) {
                        minV = Math.min(minV, DP[i - 1][j] + b);
                    }

                    DP[i][j] = minV;
                }
            }
        }

        int idx = onboard.length - 1;
        int answer = MAX_V;
        for (int i = 0; i < MAX_TEMP + 1; i++) {
            answer = Math.min(answer, DP[idx][i]);
        }
        return answer;
    }
}