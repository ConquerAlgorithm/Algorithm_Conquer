package BackJoon.실버.S2.BJ_2630_색종이_만들기;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static final int BLUE = 1;
    static final int WHITE = 0;

    static int N, w_cnt, b_cnt;
    static boolean[][] board;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        board = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int n = Integer.parseInt(st.nextToken());

                board[i][j] = n == BLUE;
            }
        }

        dq(0, 0, N);

        System.out.println(w_cnt);
        System.out.println(b_cnt);
    }

    static void dq(int x, int y, int size) {
        if (size == 1) {
            if (board[x][y]) b_cnt++;
            else w_cnt++;
            return;
        }
        if (isAll(x, y, size)) {
            if (board[x][y]) b_cnt++;
            else w_cnt++;
            return;
        }

        size /= 2;

        dq(x, y, size);
        dq(x, y + size, size);
        dq(x + size, y, size);
        dq(x + size, y + size, size);
    }

    static boolean isAll(int x, int y, int size) {
        boolean check = board[x][y];

        for (int i = x; i < x + size; i++) {
            for (int j = y; j < y + size; j++) {
                if (check ^ board[i][j]) return false;
            }
        }

        return true;
    }
}
