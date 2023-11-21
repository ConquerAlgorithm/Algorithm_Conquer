package Programmers.LV3;
import java.util.*;

public class 아이템줍기 {

    class Solution {

        final int MAX_ROW = 51;
        final int MAX_COL = 51;

        final int[] dx = {-1, 0, 1, 0};
        final int[] dy = {0, -1, 0, 1};

        class Point {
            int x, y, dist;

            public Point(int x, int y, int dist) {
                this.x = x;
                this.y = y;
                this.dist = dist;
            }
        }

        int ans;
        int[][] board;
        boolean[][] internal;
        Point start, end;

        public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
            board = new int[MAX_ROW * 2][MAX_COL * 2];
            internal = new boolean[MAX_ROW * 2][MAX_COL * 2];

            start = new Point(characterX * 2, characterY * 2, 0);
            end = new Point(itemX * 2, itemY * 2, 0);

            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = 0;
            int maxY = 0;

            // 일단 주어진 입력대로 테두리만 그린다.
            for (int i = 0; i < rectangle.length; i++) {
                int x1 = rectangle[i][0] * 2;
                int y1 = rectangle[i][1] * 2;
                int x2 = rectangle[i][2] * 2;
                int y2 = rectangle[i][3] * 2;

                mkRectangle(x1, y1, x2, y2);
            }

            BFS();

            return ans / 2;       // 캐릭터가 아이템으로 가는 최단 거리
        }

        void BFS() {
            Queue<Point> q = new LinkedList<>();
            boolean[][] visited = new boolean[MAX_ROW * 2][MAX_COL * 2];

            q.add(new Point(start.x, start.y, 0));
            visited[start.x][start.y] = true;

            while (!q.isEmpty()) {
                Point cur = q.poll();

                int cx = cur.x;
                int cy = cur.y;

                if (cx == end.x && cy == end.y) {
                    ans = cur.dist;
                    break;
                }

                for (int d = 0; d < 4; d++) {
                    int nx = cx + dx[d];
                    int ny = cy + dy[d];

                    if (isNotBoundary(nx, ny)) continue;
                    if (visited[nx][ny]) continue;
                    if (board[nx][ny] == 0) continue;

                    q.add(new Point(nx, ny, cur.dist + 1));
                    visited[nx][ny] = true;
                }
            }
        }

        boolean isNotBoundary(int x, int y) {
            return !(0 <= x && x <= MAX_ROW * 2 && 0 <= y && y <= MAX_COL * 2);
        }

        void mkRectangle(int x1, int y1, int x2, int y2) {
            for (int r = x1; r <= x2; r++) {
                for (int c = y1; c <= y2; c++) {
                    // 테두리이고 다른 사각형의 내부이면
                    if (!internal[r][c] && (r == x1 || c == y1 || r == x2 || c == y2)) board[r][c] = 1;
                    else {
                        internal[r][c] = true;
                        board[r][c] = 0;
                    }
                }
            }
        }

        void printBoard() {
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++) {
                    if (i == start.x && j == start.y) System.out.print("S ");
                    else if (i == end.x && j == end.y) System.out.print("E ");
                    else System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
