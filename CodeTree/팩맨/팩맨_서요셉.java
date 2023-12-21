package CodeTree.삼성스터디.팩맨;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 팩맨 {

    static final int MAX_LIFE = 2;
    static final int P_DIR_SET = 4;
    static final int M_DIR_SET = 8;
    static final int MAX_T = 25;

    // 반시계 8방
    static int[] mdx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] mdy = {0, -1, -1, -1, 0, 1, 1, 1};

    // 상 좌 하 우
    static int[] pdx = {-1, 0, 1, 0};
    static int[] pdy = {0, -1, 0, 1};

    static int N = 4;
    static int M, T;
    static int px, py;
    static int tNum = 1;

    static int[][][][] monsterBoard = new int[MAX_T + 1][N][N][M_DIR_SET];
    static int[][][] deadBoard = new int[N][N][MAX_LIFE + 1];

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        px = Integer.parseInt(st.nextToken()) - 1;
        py = Integer.parseInt(st.nextToken()) - 1;

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;

            monsterBoard[0][x][y][d]++;
        }
    }

    public static void main(String[] args) throws Exception {
        input();

        while (tNum <= T) {
            solve();
            tNum++;
        }

        System.out.println(cntMonster());
    }

    static void solve() {
        moveMonster();
        movePack();
        deadMonster();
        cloneMonster();
    }

    static void moveMonster() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M_DIR_SET; k++) {
                    Monster nPoint = getNextPoint(i, j, k);

                    int x = nPoint.x;
                    int y = nPoint.y;
                    int nd = nPoint.dir;

                    monsterBoard[tNum][x][y][nd] += monsterBoard[tNum - 1][i][j][k];
                }
            }
        }
    }

    static Monster getNextPoint(int x, int y, int moveDir) {
        for (int cd = 0; cd < M_DIR_SET; cd++) {
            int nd = (moveDir + cd + M_DIR_SET) % M_DIR_SET;

            int nx = x + mdx[nd];
            int ny = y + mdy[nd];

            if (canMove(nx, ny)) return new Monster(nx, ny, nd);
        }

        return new Monster(x, y, moveDir);
    }

    static void movePack() {
        int max = -1;
        Route best = new Route(-1, - 1, -1);

        for (int i = 0; i < P_DIR_SET; i++) {
            for (int j = 0; j < P_DIR_SET; j++) {
                for (int k = 0; k < P_DIR_SET; k++) {
                    int mCnt = eatCntMonster(i, j, k);

                    if (mCnt > max) {
                        max = mCnt;
                        best = new Route(i, j, k);
                    }
                }
            }
        }

        realKill(best);
    }

    static int eatCntMonster(int d1, int d2, int d3) {
        int[] dirs = {d1, d2, d3};
        int x = px;
        int y = py;
        int killCnt = 0;

        List<Point> vList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int nx = x + pdx[dirs[i]];
            int ny = y + pdy[dirs[i]];

            if (!isBoundary(nx, ny)) return -1;
            if (!visited(vList, new Point(nx, ny))) {
                for (int j = 0; j < M_DIR_SET; j++) {
                    killCnt += monsterBoard[tNum][nx][ny][j];
                }

                vList.add(new Point(nx, ny));
            }

            x = nx;
            y = ny;
        }

        return killCnt;
    }

    static boolean visited(List<Point> vList, Point p) {
        for (int i = 0; i < vList.size(); i++) {
            if (vList.get(i).isSame(p)) return true;
        }
        return false;
    }

    static void realKill(Route r) {
        int d1 = r.d1;
        int d2 = r.d2;
        int d3 = r.d3;

        int[] dirs = {d1, d2, d3};
        for (int i = 0; i < 3; i++) {
            int nx = px + pdx[dirs[i]];
            int ny = py + pdy[dirs[i]];

            for (int j = 0; j < M_DIR_SET; j++) {
                deadBoard[nx][ny][MAX_LIFE] += monsterBoard[tNum][nx][ny][j];
                monsterBoard[tNum][nx][ny][j] = 0;
            }

            px = nx;
            py = ny;
        }
    }

    static void deadMonster() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < MAX_LIFE; k++) {
                    deadBoard[i][j][k] = deadBoard[i][j][k + 1];
                }
                deadBoard[i][j][MAX_LIFE] = 0;
            }
        }
    }

    static void cloneMonster() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M_DIR_SET; k++) {
                    monsterBoard[tNum][i][j][k] += monsterBoard[tNum - 1][i][j][k];
                }
            }
        }
    }

    static int cntMonster() {
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < M_DIR_SET; k++) {
                    cnt += monsterBoard[T][i][j][k];
                }
            }
        }

        return cnt;
    }

    static boolean canMove(int x, int y) {
        return isBoundary(x, y) && deadBoard[x][y][0] == 0 && deadBoard[x][y][1] == 0
                && !new Point(x, y).isSame(new Point(px, py));
    }

    static boolean isBoundary(int x, int y) {
        return 0 <= x && x < N && 0 <= y && y < N;
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isSame(Point p) {
            return this.x == p.x && this.y == p.y;
        }
    }

    static class Monster extends Point {
        int dir;

        public Monster(int x, int y, int dir) {
            super(x, y);
            this.dir = dir;
        }
    }

    static class Route {
        int d1, d2, d3;

        public Route(int d1, int d2, int d3) {
            this.d1 = d1;
            this.d2 = d2;
            this.d3 = d3;
        }
    }
}