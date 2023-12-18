package BackJoon.골드.G5.BJ_7569_토마토;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_7569_토마토 {

    static final int O_T = 1;
    static final int X_T = 0;
    static final int NONE_T = -1;

    static final int[] dh = {0, 0, 0, 0, -1, 1};
    static final int[] dx = {-1, 0, 1, 0, 0, 0};
    static final int[] dy = {0, -1, 0, 1, 0, 0};

    static int N, M, H;
    static int[][][] board;

    static Queue<Point> q;
    static boolean[][][] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        board = new int[H][N][M];
        q = new LinkedList<>();
        visited = new boolean[H][N][M];

        for (int h = 0; h < H; h++) {
            for (int n = 0; n < N; n++) {
                st = new StringTokenizer(br.readLine());
                for (int m = 0; m < M; m++) {
                    board[h][n][m] = Integer.parseInt(st.nextToken());
                }
            }
        }

        for (int h = 0; h < H; h++) {
            for (int n = 0; n < N; n++) {
                for (int m = 0; m < M; m++) {
                    if (board[h][n][m] == O_T && !visited[h][n][m]) {
                        q.add(new Point(h, n, m, 0));
                        visited[h][n][m] = true;
                    }
                }
            }
        }

        int ans = 0;
        if (isFullOne()) {
            System.out.println(ans);
        } else {
            BFS();
            ans = getAns();
            ans = ans == 0 ? -1 : ans;
            System.out.println(ans);
        }

    }

    static boolean isFullOne() {

        for (int h = 0; h < H; h++) {
            for (int n = 0; n < N; n++) {
                for (int m = 0; m < M; m++) {
                    if (board[h][n][m] == 0) return false;
                }
            }
        }

        return true;
    }

    static int getAns() {
        int res = 0;

        for (int h = 0; h < H; h++) {
            for (int n = 0; n < N; n++) {
                for (int m = 0; m < M; m++) {
                    if (board[h][n][m] == 0) return 0;
                    if (board[h][n][m] == -1) continue;

                    res = Math.max(res, board[h][n][m]);
                }
            }
        }

        return res;
    }

    static void BFS() {

        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                Point cur = q.poll();

                int ch = cur.h;
                int cx = cur.x;
                int cy = cur.y;

                for (int d = 0; d < 6; d++) {
                    int nh = ch + dh[d];
                    int nx = cx + dx[d];
                    int ny = cy + dy[d];

                    if (isNotBoundary(nh, nx, ny) || visited[nh][nx][ny] ||  board[nh][nx][ny] != X_T) continue;

                    q.add(new Point(nh, nx, ny, cur.cnt + 1));
                    visited[nh][nx][ny] = true;
                    board[nh][nx][ny] = cur.cnt + 1;
                }
            }
        }
    }

    static class Point {
        int x, y, h, cnt;

        public Point (int h, int x, int y, int cnt) {
            this.h = h;
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }
    }

    static boolean isNotBoundary(int h, int x, int y) {
        return !(0 <= h && h < H && 0 <= x && x < N && 0 <= y && y < M);
    }

    static void printBoard(int[][][] board) {
        for (int h = 0; h < H; h++) {
            for (int n = 0; n < N; n++) {
                for (int m = 0; m < M; m++) {
                    System.out.print(board[h][n][m] + "\t");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }
}
