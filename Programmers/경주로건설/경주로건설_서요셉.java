package Programmers.LV3.경주로건설;
import java.util.*;

public class 경주로건설 {

    class Solution {

        final int INF = Integer.MAX_VALUE;
        final int[] dx = {-1, 0, 1, 0};
        final int[] dy = {0, 1, 0, -1};

        int N, answer;
        int[][] board;
        boolean[][][] visited;

        public int solution(int[][] b) {

            N = b.length;
            answer = INF;
            board = b;
            visited = new boolean[N][N][4];

            BFS();

            return answer;
        }

        void BFS() {
            Queue<Point> q = new LinkedList<>();
            q.add(new Point(0, 0, -1, 0));

            while (!q.isEmpty()) {
                Point cur = q.poll();

                int cx = cur.x;
                int cy = cur.y;
                int cd = cur.d;

                if (cx == N - 1 && cy == N - 1) {
                    answer = Math.min(answer, cur.c);
                }

                for (int d = 0; d < 4; d++) {
                    int nx = cx + dx[d];
                    int ny = cy + dy[d];

                    if (isNotBoundary(nx, ny) || board[nx][ny] == 1) continue;

                    int nextCost = cur.c;
                    if (cd == -1 || cd == d) nextCost += 100;
                    else nextCost += 600;

                    if (!visited[nx][ny][d] || board[nx][ny] >= nextCost) {
                        board[nx][ny] = nextCost;
                        q.add(new Point(nx, ny, d, nextCost));
                        visited[nx][ny][d] = true;
                    }
                }
            }
        }

        boolean isNotBoundary(int x, int y) {
            return !(0 <= x && x < N && 0 <= y && y < N);
        }

        class Point {
            int x, y, d, c;

            public Point(int x, int y, int d, int c) {
                this.x = x;
                this.y = y;
                this.d = d;
                this.c = c;
            }
        }
    }
}
