package BackJoon.골드.G3.BJ_6087_레이저통신;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class 레이저통신 {

    static class Point implements Comparable<Point> {
        int x, y, dir, mirrors;

        public Point(int x, int y, int dir, int mirrors) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.mirrors = mirrors;
        }

        @Override
        public int compareTo(Point p) {
            return this.mirrors - p.mirrors;
        }
    }

    static int N, M, ans;
    static char[][] board;
    static Point[] target;

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, -1, 0, 1};

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        board = new char[N][M];
        target = new Point[2];

        int idx = 0;
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = input.charAt(j);

                if (board[i][j] == 'C') target[idx++] = new Point(i, j, -10, -1);
            }
        }

    }
    public static void main(String[] args) throws Exception {
        input();

        BFS(target[0]);

        System.out.println(ans);
    }

    static void BFS(Point s) {
        Point e = target[1];
        ans = Integer.MAX_VALUE;

        PriorityQueue<Point> pq = new PriorityQueue<>();
        int[][][] visited = new int[4][N][M];
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i < N; i++) {
                Arrays.fill(visited[d][i], Integer.MAX_VALUE);
            }
        }

        pq.add(s);

        while (!pq.isEmpty()) {
            Point cur = pq.poll();

            int cx = cur.x;
            int cy = cur.y;

            if (cx == e.x && cy == e.y) {
                ans = Math.min(ans, cur.mirrors);
                continue;
            }

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];
                int nMirrors = (cur.dir == d) ? cur.mirrors : cur.mirrors + 1;

                if (isNotBoundary(nx, ny) || board[nx][ny] == '*' || Math.abs(cur.dir - d) == 2) continue;

                if (visited[d][nx][ny] > nMirrors) {
                    visited[d][nx][ny] = nMirrors;
                    pq.add(new Point(nx, ny, d, nMirrors));
                }
            }
        }
    }

    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < M);
    }

}
