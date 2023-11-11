package BackJoon.골드.G5.BJ_5582_공통부분문자열;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BJ_5582_공통_부분_문자열 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input1 = br.readLine();
        String input2 = br.readLine();

        int len1 = input1.length();
        int len2 = input2.length();

        // 최장 길이 변수 - 갱신 필요
        int max = 0;

        // 길이 갱신 board
        int[][] board = new int[len1 + 1][len2 + 2];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (input1.charAt(i - 1) == input2.charAt(j - 1)) {
                    board[i][j] = board[i - 1][j - 1] + 1;
                    max = Math.max(max, board[i][j]);
                }
            }
        }

        System.out.println(max);
    }
}
