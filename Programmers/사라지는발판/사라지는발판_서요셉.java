package Programmers.LV3.사라지는벌판;

public class 사라지는벌판 {
    class Solution {

        final int MAX_ROW = 5;
        final int MAX_COL = 5;
        final int DIR_NUM = 4;

        class Result {
            boolean win;
            int cnt;

            public Result(boolean win, int cnt) {
                this.win = win;
                this.cnt = cnt;
            }
        }

        int N, M;
        int[][] b;

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        public int solution(int[][] board, int[] aloc, int[] bloc) {
            b = board;
            N = b.length;
            M = b[0].length;

            return DFS(aloc[0], aloc[1], bloc[0], bloc[1], 0).cnt;
        }

        Result DFS(int ax, int ay, int bx, int by, int depth) {
            // 현재 플레이어가 움직이지 못할 경우 -> 현재 플레이어 패배
            if (isEnd(ax, ay)) return new Result(false, 0);

            // 현재 플레이어와 다음 플레이어가 같은 위치인 경우 -> 현재 플레이어 승리
            if (ax == bx && ay == by) return new Result(true, 1);

            // 다음 단계로 이동하여 결과 확인 -> 이동수 1 증가
            boolean win = false;
            int minCnt = MAX_ROW * MAX_COL;
            int maxCnt = 0;

            b[ax][ay] = 0;

            for (int d = 0; d < DIR_NUM; d++) {
                int nx = ax + dx[d];
                int ny = ay + dy[d];

                if (canMove(nx, ny)) {
                    Result nRes = DFS(bx, by, nx, ny, depth + 1);

                    if (nRes.win) maxCnt = Math.max(maxCnt, nRes.cnt);
                    else {
                        win = true;
                        minCnt = Math.min(minCnt, nRes.cnt);
                    }
                }
            }

            b[ax][ay] = 1;
            return new Result(win, (win ? minCnt : maxCnt) + 1);
        }

        boolean isEnd(int x, int y) {
            for (int d = 0; d < DIR_NUM; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                // 움직일 수 있으면 끝이 아님
                if (canMove(nx, ny)) return false;
            }

            return true;
        }

        boolean canMove(int x, int y) {
            return isBoundary(x, y) && b[x][y] == 1;
        }

        boolean isBoundary(int x, int y) {
            return 0 <= x && x < N && 0 <= y && y < M;
        }
    }
}
