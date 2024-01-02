package BackJoon.실버.S1.BJ_2468_안전_영역;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/*
5
9 9 9 9 9
9 9 9 9 9
9 9 9 9 9
9 9 9 9 9
9 9 9 9 9
 */
public class Main {

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, -1, 0, 1};

    static int N, maxHeight, ans;
    static int[][] board;
    static boolean[][] rained;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        board = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
                maxHeight = Math.max(maxHeight, board[i][j]);
            }
        }

        // 1 ~ maxHeight 까지 높여가면서 안전영역을 cnt 하고 max 로 갱신한다.
        for (int h = 0; h < maxHeight; h++) {
            rain(h);
        }

        System.out.println(ans);
    }

    static void rain(int height) {
        // height 에 따라 잠기는 영역을 체크할 rained 초기화
        rained = new boolean[N][N];

        // 안전영역 cnt 변수
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!rained[i][j] && board[i][j] > height) {
                    cnt++;
                    BFS(i, j, height);
                }
            }
        }

        ans = Math.max(ans, cnt);
    }

    static void BFS(int x, int y, int height) {
        Queue<Point> q = new LinkedList<>();

        q.add(new Point(x, y));
        rained[x][y] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            int cx = cur.x;
            int cy = cur.y;

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (isNotBoundary(nx, ny) || rained[nx][ny] || board[nx][ny] <= height) continue;

                q.add(new Point(nx, ny));
                rained[nx][ny] = true;
            }
        }
    }

    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < N);
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
