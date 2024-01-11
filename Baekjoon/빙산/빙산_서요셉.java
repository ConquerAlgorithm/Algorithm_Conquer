package BackJoon.골드.G4.BJ_2573_빙산;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/*
5 5
0 0 0 0 0
0 2 1 2 0
0 1 4 1 0
0 2 1 2 0
0 0 0 0 0

 */
public class Main {

    static int N, M, ans;
    static int[][] board;
    static boolean[][] visited;

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, -1, 0, 1};

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        board = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        input();

        int year = 0;
        boolean flag = false;

        while (!isNone()) {
            year++;

            // 매년 얼음이 녹는 과정
            meltIce();
//            printB(board);

            // 빙산 덩어리가 두 덩어리 이상이면 멈춘다.
            if (isOverTwo()) {
                flag = true;
                break;
            }
        }

        // 만약 다 녹았다면 0출력
        year = flag ? year : 0;
        System.out.println(year);
    }

    static void meltIce() {
        int[][] copyBoard = copyBoard(board);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int cnt = 0;

                if (board[i][j] != 0) {
                    // 4방의 바닷물 수 세기
                    for (int d = 0; d < 4; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];

                        if (isNotBoundary(nx, ny)) continue;

                        if (board[nx][ny] == 0) cnt++;
                    }
                }

                // 바닷물 수 만큼 copyBoard 에서 녹인다.
                copyBoard[i][j] = Math.max(0, copyBoard[i][j] - cnt);
            }
        }

        board = copyBoard(copyBoard);
    }

    // 빙산 덩어리가 두 덩어리 이상이면
    static boolean isOverTwo() {
        visited = new boolean[N][M];
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] != 0 && !visited[i][j]) {
                    cnt += BFS(i, j);
                }
            }
        }

        return cnt >= 2;
    }

    static int BFS(int x, int y) {
        Queue<Point> q = new LinkedList<>();

        q.add(new Point(x, y));
        visited[x][y] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            int cx = cur.x;
            int cy = cur.y;

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (isNotBoundary(nx, ny) || visited[nx][ny] || board[nx][ny] == 0) continue;

                q.add(new Point(nx,ny));
                visited[nx][ny] = true;
            }
        }

        return 1;
    }

    static boolean isNone() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] != 0) return false;
            }
        }

        return true;
    }

    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < M);
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int[][] copyBoard(int[][] b) {
        int[][] tmp = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                tmp[i][j] = b[i][j];
            }
        }

        return tmp;
    }

    static void printB(int[][] b) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(b[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
