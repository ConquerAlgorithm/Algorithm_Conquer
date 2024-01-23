package BackJoon.골드.G1.BJ_13460_구슬탈출2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final int UP = 0;
    static final int RIGHT = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static int N, M;
    static char[][] board;
    static boolean[][][][] visited;

    static Point red, blue;
    static int hx, hy;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new char[N][M];
        visited = new boolean[N][M][N][M];

        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = input.charAt(j);

                switch (board[i][j]) {
                    case 'R':
                        red = new Point(i, j, 0, 0, 0);
                        break;
                    case 'B':
                        blue = new Point(0, 0, i, j, 0);
                        break;
                    case 'O':
                        hx = i;
                        hy = j;
                        break;
                }
            }
        }

    }
    public static void main(String[] args) throws Exception {
        input();
        System.out.println(process());
    }

    static int process() {
        Queue<Point> q = new LinkedList<>();

        q.add(new Point(red.rx, red.ry, blue.bx, blue.by, 1));
        visited[red.rx][red.ry][blue.bx][blue.by] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            int crx = cur.rx;
            int cry = cur.ry;
            int cbx = cur.bx;
            int cby = cur.by;
            int curCnt = cur.cnt;

            if (curCnt > 10) return -1;

            for (int d = 0; d < 4; d++) {
                int nrx = crx;
                int nry = cry;
                int nbx = cbx;
                int nby = cby;

                boolean holeInRed = false;
                boolean holeInBlue = false;

                // 빨간 공 벽에 닿을 때까지 이동
                while (board[nrx + dx[d]][nry + dy[d]] != '#') {
                    nrx += dx[d];
                    nry += dy[d];

                    if (isHole(nrx, nry)) {
                        holeInRed = true;
                        break;
                    }
                }

                // 파란 공 벽에 닿을 때 까지 이동
                while (board[nbx + dx[d]][nby + dy[d]] != '#') {
                    nbx += dx[d];
                    nby += dy[d];

                    if (isHole(nbx, nby)) {
                        holeInBlue = true;
                        break;
                    }
                }

                if (holeInBlue) continue;
                if (holeInRed && !holeInBlue) return curCnt;

                if (nrx == nbx && nry == nby) {
                    switch (d) {
                        case UP:
                            if (crx > cbx) nrx -= dx[d];
                            else nbx -= dx[d];
                            break;
                        case RIGHT:
                            if (cry < cby) nry -= dy[d];
                            else nby -= dy[d];
                            break;
                        case DOWN:
                            if (crx < cbx) nrx -= dx[d];
                            else nbx -= dx[d];
                            break;
                        case LEFT:
                            if (cry > cby) nry -= dy[d];
                            else nby -= dy[d];
                            break;
                    }
                }

                if (!visited[nrx][nry][nbx][nby]) {
                    q.add(new Point(nrx, nry, nbx, nby, curCnt + 1));
                    visited[nrx][nry][nbx][nby] = true;
                }

            }
        }

        return -1;
    }

    static boolean isHole(int x, int y) {
        return x == hx && y == hy;
    }

    static class Point {
        int rx, ry, bx, by, cnt;

        public Point(int rx, int ry, int bx, int by, int cnt) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.cnt = cnt;
        }
    }
}
