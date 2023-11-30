package CodeTree.삼성스터디.술래잡기;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
5 3 1 50
2 4 1
1 4 2
4 2 1
2 4
*/

/**
 * - 개체
 *  1. 격자
 *      - N * N 크기
 *
 *  2. 술래
 *      - 정중앙 위치
 *
 *  3. 도망자
 *      - M 명 존재
 *      - 처음 지정된 곳에 서있음.
 *      - 좌우 이동, 상하 이동 유형 2가지 존재
 *      - 좌우 이동은 항상 오른쪽 보고 시작
 *      - 상하 이동은 항상 아래를 보고 시작
 *
 *  4. 나무
 *      - h 개의 나무 존재
 *      - 도망자와 초기에 겹쳐서 주어지는 것 가능
 *
 *  - 진행
 *   1. 도망자 이동
 *      - 술래와 거리가 3 이하인 도망자만 이동.
 *      - 거리 : |x1 - x2| + |y1 - y2|
 *      - 현재 방향으로 1칸 움직일 때 격자를 벗어나지 않는 경우
 *          - 움직이려는 칸이 술래라면 움직이지 않는다.
 *          - 움직이려는 칸에 술래가 없다면 해당 칸으로 이동. 해당 칸에 나무 있어도 무방
 *      - 현재 방향으로 1칸 움직일 때 격자를 벗어나는 경우
 *          - 방향을 반대로 튼다. 이후 바라보고 있는 방향으로 1칸 움직일 때 해당 위치에 술래가 없다면 1칸 이동
 *
 *   2. 술래 이동
 *      - 술래는 처음 위 방향으로 시작하여 달팽이 모양으로 움직인다. 끝에 도달하면 다시 거꾸로 중심으로 이동한다.
 *          - 방향을 2번 바꿀 때마다 이동할 거리 1씩 증가
 *      - 방향이 틀어지는 지점이라면 바로 방향을 튼다. 1,1이나 정중앙에 도착하면 아래, 위로 즉시 방향을 틀어야함
 *      - 술래가 방향을 틀면 바라보고 있는 방향 3칸 안에 있는 도망자를 잡게된다.
 *      - 하지만 나무가 놓여있다면 해당 칸의 도망자는 보이지 않아 안잡힌다.
 *      - 잡힌 도망자는 사라지고, 술래는 현재 턴을 t번째 턴이라 했을 때 t * (잡힌 도망자 수) 만큼 점수를 얻는다.
 *
 *   1,2번을 총 k번 반복
 *
 */
public class 술래잡기_Print {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // 좌우 - 오른쪽보며 시작, 상하 - 아래쪽보며 시작
    static class Runner extends Point {
        int s_dist, dir;

        public Runner(int x, int y, int dir) {
            super(x, y);
            this.dir = dir;
        }

        @Override
        public String toString() {
            return "Runner 좌표 : (" + x + ", " + y +
                    "), 술래와의 거리 : " + s_dist +
                    ", 이동방향 : " + dir +
                    '}';
        }
    }

    static class Seeker extends Point {
        int moveCnt, rotateCnt, dir;

        public Seeker(int x, int y, int moveCnt, int rotateCnt) {
            super(x, y);
            this.moveCnt = moveCnt;
            this.rotateCnt = rotateCnt;
            this.dir = 0;
        }

        @Override
        public String toString() {
            return "Seeker 좌표 : (" + x + ", " + y +
                    "), 이동횟수 : " + moveCnt +
                    ", 회전횟수 : " + rotateCnt +
                    ", 이동방향 : " + dir +
                    '}';
        }
    }

    // 상 우 하 좌
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static int N, M, H, K, ans;
    static int n_moveCnt, n_rotateCnt;
    static boolean turnStart;

    static Seeker seeker;
    static List<Runner>[][] runnerBoard;
    static List<Runner> runnerList;
    static boolean[][] isTree;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        n_moveCnt = 1;
        n_rotateCnt = 2;
        turnStart = false;

        // 술래 생성
        seeker = new Seeker(N / 2 + 1, N / 2 + 1, 0, 0);
        // 도망자 위치 담을 runnerBoard 생성
        runnerBoard = new ArrayList[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                runnerBoard[i][j] = new ArrayList<>();
            }
        }
        runnerList = new ArrayList<>();
        isTree = new boolean[N + 1][N + 1];

        // 도망자 정보 입력 및 생성
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int kind = Integer.parseInt(st.nextToken());

            int dir = (kind == 1) ? 1 : 2;

            Runner runner = new Runner(x, y, dir);
            runnerBoard[x][y].add(runner);
            runnerList.add(runner);
        }

        // 나무 위치 기록
        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            isTree[x][y] = true;
        }
    }

    public static void main(String[] args) throws Exception {
        input();

        // 초기 상태 출력
//        printStatus();
//        System.out.println("==============================");

        for (int turn = 1; turn <= K; turn++) {
//            System.out.println("================== " + turn + " 번 째 턴 ==================");
            calDist();

            // 도망자 이동
            for (Runner r: runnerList) {
                if (r.s_dist <= 3) moveRunner(r);
            }
//            System.out.println("1. 도망자 이동 후");
//            printStatus();

            // 술래 이동
            moveSeeker();
//            System.out.println("2. 술래 이동 후");
//            printStatus();

            // 술래가 보는 방향으로 최대 3칸 이내의 도망자를 잡는다. (해당 턴에 잡은 도망자 수 * 턴)
            catchRunner(turn);
//            System.out.println("3. 도망자 잡은 후");
//            printStatus();
//            System.out.println("================================================");
        }

        System.out.println(ans);
    }

    static void catchRunner(int turn) {
        int catchCnt = 0;

        // 술래가 바라보는 방향으로 최대 3칸 이내의 도망자들을 잡는다.
        // 나무와 함께 있다면 pass
        for (int v = 0; v <= 2; v++) {
            int x = seeker.x + dx[seeker.dir] * v;
            int y = seeker.y + dy[seeker.dir] * v;

            if (isNotBoundary(x, y)) break;

            // 해당 좌표에 나무가 없고 도망자가 있다면
            if (!isTree[x][y] && !runnerBoard[x][y].isEmpty()) {
                catchCnt += runnerBoard[x][y].size();

                for (Runner r : runnerBoard[x][y]) runnerList.remove(r);
                runnerBoard[x][y].clear();
            }
        }

        ans += catchCnt * turn;
    }

    static void moveRunner(Runner r) {
        // 자신들의 방향으로 이동.
        int nx = r.x + dx[r.dir];
        int ny = r.y + dy[r.dir];

        // 격자를 벗어나는 경우
        if (isNotBoundary(nx, ny)){
            // 방향을 반대로 튼다.
            r.dir = (r.dir + 2) % 4;

            nx = r.x + dx[r.dir];
            ny = r.y + dy[r.dir];

            // 만일 해당 칸에 술래가 있는 경우
            if (nx == seeker.x && ny == seeker.y) {
                nx = r.x;
                ny = r.y;
            }
        }
        // 격자를 벗어나지 않는 경우
        else {
            // 만일 해당 칸에 술래가 있는 경우
            if (nx == seeker.x && ny == seeker.y) {
                nx = r.x;
                ny = r.y;
            }
        }

        runnerBoard[r.x][r.y].remove(r);

        r.x = nx;
        r.y = ny;

        runnerBoard[r.x][r.y].add(r);
    }
    static boolean isNotBoundary(int x, int y) {
        return !(1 <= x && x <= N && 1 <= y && y <= N);
    }

    static void moveSeeker() {
        // 현재 방향으로 1칸 이동
        seeker.x = seeker.x + dx[seeker.dir];
        seeker.y = seeker.y + dy[seeker.dir];
        seeker.moveCnt++;

        if (!turnStart) {
            // 이동 횟수를 채우면 회전하고 이동 횟수를 0으로 만든다.
            if (seeker.moveCnt == n_moveCnt) {
                seeker.moveCnt = 0;
                seeker.dir = (seeker.dir + 1) % 4;
                seeker.rotateCnt++;
            }
            // 회전 횟수가 2가 되면 필요 이동 횟수를 1 올린다. 회전 횟수를 0으로 만든다.
            if (seeker.rotateCnt == n_rotateCnt) {
                n_moveCnt++;
                seeker.rotateCnt = 0;
            }
        }
        else {
            // 이동 횟수를 채우면 회전하고 이동 횟수를 0으로 만든다.
            if (seeker.moveCnt == n_moveCnt) {
                seeker.moveCnt = 0;
                seeker.dir = (seeker.dir - 1 + 4) % 4;
                seeker.rotateCnt++;
            }
            // 회전 횟수가 2가 되면 필요 이동 횟수를 1 올린다. 회전 횟수를 0으로 만든다.
            if (seeker.rotateCnt == n_rotateCnt) {
                n_moveCnt--;
                seeker.rotateCnt = 0;
            }
        }

        if (seeker.x == 1 && seeker.y == 1) {
            turnStart = true;
            seeker.dir = 2;
            seeker.moveCnt = 1;
            seeker.rotateCnt = 1;
        } else if (seeker.x == N / 2 + 1 && seeker.y == N / 2 + 1) {
            turnStart = false;
            seeker.dir = 0;
            n_moveCnt = 1;
        }
    }

    static void calDist() {
        for (Runner r: runnerList) {
            r.s_dist = Math.abs(r.x - seeker.x) + Math.abs(r.y - seeker.y);
        }
    }

    static void printStatus() {
        printRunnerStatus();
        printRunnerBoard();
        printSeekerStatus();
        printSeekerBoard();
    }

    static void printRunnerBoard() {
        System.out.println(":: 도망자 위치 정보 ::");
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.print(runnerBoard[i][j].size() + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printRunnerStatus() {
        System.out.println(":: 도망자 상태 ::");
        for (Runner r: runnerList) {
            System.out.println(r.toString());
        }
        System.out.println();
    }

    static void printSeekerBoard() {
        System.out.println(":: 술래 위치 정보 ::");
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == seeker.x && j == seeker.y && seeker.dir == 0) System.out.print("상\t");
                else if (i == seeker.x && j == seeker.y && seeker.dir == 1) System.out.print("우\t");
                else if (i == seeker.x && j == seeker.y && seeker.dir == 2) System.out.print("하\t");
                else if (i == seeker.x && j == seeker.y && seeker.dir == 3) System.out.print("좌\t");
                else System.out.print("0\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printSeekerStatus() {
        System.out.println(":: 술래 상태 ::");
        System.out.println(seeker.toString());
        System.out.println();
    }
}