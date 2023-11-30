package BackJoon.실버.S1.BJ_2178_미로탐색;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_2178_미로탐색_재풀이 {

    static int N, M, ans;
    static char[][] board;

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new char[N][M];

        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = input.charAt(j);
            }
        }
    }
    public static void main(String[] args) throws Exception {
        input();

        bfs();

        System.out.println(ans);
    }

    static void bfs() {
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];

        q.add(new int[]{0, 0, 1});
        visited[0][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            int cx = cur[0];
            int cy = cur[1];

            if (cx == N - 1 && cy == M - 1) {
                ans = cur[2];
                break;
            }

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (isNotBoundary(nx, ny)) continue;
                if (visited[nx][ny]) continue;
                if (board[nx][ny] == '0') continue;

                q.add(new int[]{nx, ny, cur[2] + 1});
                visited[nx][ny] = true;
            }
        }
    }

    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < M);
    }

    static void printBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {

                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
