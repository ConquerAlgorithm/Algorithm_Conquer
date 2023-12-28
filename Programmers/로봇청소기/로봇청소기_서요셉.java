package BackJoon.골드.G5.BJ_14503_로봇청소기;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 로봇청소기 {

    static final int WALL = 1;
    static final int CLEANED = -1;
    static final int NON_CLEANED = 0;
    static final int DIR_NUM = 4;

    // 상 우 하 좌
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static int N, M, ans;
    static int[][] board;
    static int rx, ry, rd;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][M];

        st = new StringTokenizer(br.readLine());
        rx = Integer.parseInt(st.nextToken());
        ry = Integer.parseInt(st.nextToken());
        rd = Integer.parseInt(st.nextToken());
        ans = 1;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        input();
        DFS(rx, ry, rd);
        System.out.println(ans);
    }

    static void DFS(int x, int y, int dir) {
        board[x][y] = CLEANED;

        for (int d = 0; d < 4; d++) {
            dir = (dir - 1 + DIR_NUM) % DIR_NUM;

            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (isBoundary(nx, ny) && board[nx][ny] == NON_CLEANED) {
                ans++;
                DFS(nx, ny, dir);
                return;
            }
        }

        int back = (dir + 2) % DIR_NUM;

        int nx = x + dx[back];
        int ny = y + dy[back];

        if (isBoundary(nx, ny) && board[nx][ny] != WALL) {
            DFS(nx, ny, dir);
        }
    }

    static boolean isBoundary(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < M;
    }
}