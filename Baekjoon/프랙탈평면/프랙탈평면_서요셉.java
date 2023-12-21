package BackJoon.골드.G3.BJ_1030_프랙탈평면;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 프랙탈평면 {

    static int s, N, K, R1, R2, C1, C2;
    static char[][] board = new char[51][51];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        s = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        R1 = Integer.parseInt(st.nextToken());
        R2 = Integer.parseInt(st.nextToken());
        C1 = Integer.parseInt(st.nextToken());
        C2 = Integer.parseInt(st.nextToken());

        fractal(0, 0, (int)Math.pow(N, s), false);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= R2 - R1; i++) {
            for (int j = 0; j <= C2 - C1; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }

    static void fractal(int x, int y, int size, boolean isBlack) {
        if (x + size <= R1 || x > R2 || y + size <= C1 || y > C2) return;
        if (size == 1) {
            board[x - R1][y - C1] = isBlack ? '1' : '0';
            return;
        }

        int nSize = size / N;
        int start_black = (N - K) / 2;
        int end_black = N - start_black;

        for (int i = 0; i < N; i++) {
            int nx = x + nSize * i;
            for (int j = 0; j < N; j++) {
                int ny = y + nSize * j;

                fractal(nx, ny, nSize, isBlack || (i >= start_black && i < end_black) && (j >= start_black && j < end_black));
            }
        }
    }
}
