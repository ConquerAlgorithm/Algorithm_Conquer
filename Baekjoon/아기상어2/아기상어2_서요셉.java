package BackJoon.실버.S2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * - 안전거리 : 그 칸과 가장 거리가 가까운 상어와의 거리.
 * - 두칸의 거리는 하나의 칸에서 다른 칸으로 가기위해 지나야 하는 칸의 수
 * - 이동은 인접한 8방으로 이동
 */
public class BJ_17086_아기상어2 {

    static class Point {
        int x, y, dist;

        public Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    static int N, M;
    static int[][] board;
    static int[][] dist;

    // 상 우상 우 우하 하 좌하 좌 좌상
    static final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static final int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N][M];
        dist = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < M; j++) {
                int num = Integer.parseInt(st.nextToken());
                board[i][j] = num;

                if (num == 1) BFS(i, j);
            }
        }

        System.out.println(findAns());
    }

    static int findAns() {
        int ans = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, dist[i][j]);
            }
        }

        return ans;
    }

    static void BFS(int x, int y) {
        Queue<Point> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];

        q.add(new Point(x, y, 0));
        visited[x][y] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            int cx = cur.x;
            int cy = cur.y;

            // 만일 현재 좌표가 0이 아니라면 최소값을 저장
            if (dist[cx][cy] != 0) {
                int min = Math.min(cur.dist, dist[cx][cy]);
                dist[cx][cy] = min;
            } else {
                // 현재 좌표에 거리를 마킹
                dist[cx][cy] = cur.dist;
            }

            for (int d = 0; d < 8; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (isNotBoundary(nx, ny)) continue;
                if (visited[nx][ny]) continue;
                if (board[nx][ny] == 1) continue;

                q.add(new Point(nx, ny, cur.dist + 1));
                visited[nx][ny] = true;
            }
        }
    }


    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < M);
    }

    static void test_dist() {
        System.out.println();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(dist[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
