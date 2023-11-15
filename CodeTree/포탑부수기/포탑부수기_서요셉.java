package CodeTree.삼성스터디.포탑부수기;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * - 개체
 *  1. 격자
 *      - N * M  격자 (포탑 개수는 NM개)
 *      - 최초 공격력이 0 인 포탑 존재 가능
 *
 *  2. 포탑
 *      - 공격력이 존재. 상황에 따라 증감
 *      - 공격력이 0 되면 파괴, 더이상의 공격 불가
 *
 *
 * - 진행
 *  1. 공격자 선정 (가장 약한 포탑 선정)
 *      - 선정 기준
 *          * 공격력이 가장 낮은 것
 *          * 여러개라면 가장 최근에 공격한 것
 *          * 여러개라면 행 + 열 값이 가장 큰 것
 *          * 여러개라면 행 값이 가장 큰 것
 *          * 여러개라면 열 값이 가장 큰 것
 *      - 가장 약한 포탑은 선정되면 N + M 만큼 공격력이 증가.
 *
 *  2. 공격자 공격 (공격 대상으로 가장 강한 포탑 선정)
 *      - 선정 기준
 *          * 공격력이 가장 높은 것
 *          * 여러개라면 공격한지 가장 오래된 것
 *          * 여러개라면 행 + 열 값이 가장 작은 것
 *          * 여러개라면 행 값이 가장 작은 것
 *          * 여러개라면 열 값이 가장 큰 것
 *
 *      2-1. 최단 경로로 이동 가능할 시 레이저
 *      - 우하좌상 우선순위로 4방 이동.
 *      - 부서진 포탑은 지날 수 없음.
 *      - 가장자리에서는 반대로 나오게 된다.
 *
 *      2-2. 최단 경로로 이동 불가능할 시 포탄
 *      - 8방 범위의 포탑에 공격.
 *      - 가장자리에 떨어지면 반대편 격자에도 미친다.
 *
 *      2-3. 공격 피해
 *      - 공격 대상은 공격자 공격력만큼 피해를 입고 해당 수치만큼 공격력이 감소함.
 *      - 레이저 경로에 있거나 포탄 범위에 있던 포탑들은 공격자 공격력의 절반만큼 피해를 받는다.
 *
 *  3. 포탑 파괴
 *      - 공격력이 0 이면 부서진다.
 *
 *  4. 포탑 정비
 *      - 공격과 관련없은 포탑들은 공격력 1 증가.
 *
 * - 정답 : 모든 턴이 끝난 후 남아있는 포탑 중 가장 강한 포탑의 공격력을 출력
 *
 */
public class 포탑부수기 {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Tower extends Point implements Comparable<Tower> {
        int power, turn;
        boolean relatedAttack;

        public Tower(int x, int y, int power, int turn) {
            super(x, y);
            this.power = power;
            this.turn = turn;
            this.relatedAttack = false;
        }

        @Override
        public int compareTo(Tower t) {
            if (t.power != this.power) return this.power - t.power;
            if (t.turn != this.turn) return t.turn - this.turn;
            if ((t.x + t.y) != (this.x + this.y)) return (t.x + t.y) - (this.x + this.y);
            return t.y - this.y;
        }

        @Override
        public String toString() {
            return "Tower 좌표 : (" + x +
                    ", " + y +
                    "), power=" + power +
                    ", turn=" + turn +
                    ", relatedAttack=" + relatedAttack +
                    '}';
        }
    }

    static int N, M, K, ans;

    static List<Tower> towerList;
    static Point[][] prev;
    static Tower[][] board;
    static Tower weakestTower, strongestTower;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new Tower[N][M];
        towerList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int power = Integer.parseInt(st.nextToken());

                if (power > 0) {
                    Tower newTower = new Tower(i, j, power, 0);
                    board[i][j] = newTower;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        input();

        // K 턴 동안 진행
        for (int i = 1; i <= K; i++) {
            initTower();

            // 만약 살아남은 포탑이 하나라면 즉시 중지
            if (towerList.size() == 1) {
                ans = towerList.get(0).power;
                break;
            }

            // 가장 약한 포탑, 가장 강한 포탑 선정
            weakestTower = towerList.get(0);
            weakestTower.power += (N + M);
            weakestTower.turn = i;
            weakestTower.relatedAttack = true;

            strongestTower = towerList.get(towerList.size() - 1);
            strongestTower.relatedAttack = true;

            // 공격자의 공격
            if (isAvailableLaser()) {
                attackLaser();
            }
            else {
                attackBomb();
            }

            // 포탑 재정비
            repairTower();
        }

        int ans = findAnswer();
        System.out.println(ans);
    }

    static boolean isAvailableLaser() {
        // 가장 약한 포탑으로부터 가장 강한 포탑으로 가는 최단 거리가 존재하는지 판단.
        Queue<Point> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];

        q.add(new Point(weakestTower.x, weakestTower.y));
        visited[weakestTower.x][weakestTower.y] = true;

        // 우 하 좌 상
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        while (!q.isEmpty()) {
            Point cur = q.poll();

            int cx = cur.x;
            int cy = cur.y;

            if (cx == strongestTower.x && cy == strongestTower.y) {
                return true;
            }

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                // 가장자리라면 반대쪽으로 나오게 된다.
                if (nx < 0) nx = N - 1;
                if (nx >= N) nx = 0;
                if (ny < 0) ny = M - 1;
                if (ny >= M) ny = 0;

                // 공격력이 0인 포탑 위치는 갈 수 없다.
                if (visited[nx][ny]) continue;
                if (board[nx][ny] == null) continue;

                q.add(new Point(nx, ny));
                visited[nx][ny] = true;
                prev[nx][ny] = cur;
            }
        }

        return false;
    }


    static void attackLaser() {
        // prev 배열을 이용하여 역으로 추적하면서 공격관련 체크, 공격력 감소 로직 진행
        Point back = new Point(strongestTower.x, strongestTower.y);

        while (back.x != weakestTower.x || back.y != weakestTower.y) {
            int power = weakestTower.power / 2;

            if (back.x == strongestTower.x && back.y == strongestTower.y) {
                power = weakestTower.power;
            }

            attack(back.x, back.y, power);
            back = prev[back.x][back.y];
        }
    }

    static void attack(int x, int y, int power) {
        board[x][y].power = Math.max(board[x][y].power - power, 0);
        board[x][y].relatedAttack = true;

        if (board[x][y].power == 0) {
            board[x][y] = null;
        }
    }

    static void attackBomb() {
        // 공격 대상 주변 8 방의 타워가 공격을 받는다.
        // 본인, 상 우상 우 우하 하 좌하 좌 좌상
        int[] dx = {0, -1, -1, 0, 1, 1, 1, 0, -1};
        int[] dy = {0, 0, 1, 1, 1, 0, -1, -1, -1};

        for (int d = 0; d < 9; d++) {
            int nx = strongestTower.x + dx[d];
            int ny = strongestTower.y + dy[d];

            // 가장자리라면 반대쪽으로 나오게 된다.
            if (nx < 0) nx = N - 1;
            if (nx >= N) nx = 0;
            if (ny < 0) ny = M - 1;
            if (ny >= M) ny = 0;

            if (board[nx][ny] == null) continue;
            // 자기 자신은 영향을 받지 않는다.
            if (nx == weakestTower.x && ny == weakestTower.y) continue;

            int power = ((nx == strongestTower.x) && (ny == strongestTower.y)) ? weakestTower.power : weakestTower.power / 2;
            attack(nx, ny, power);
        }
    }

    static void repairTower() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] != null && !board[i][j].relatedAttack) {
                    board[i][j].power++;
                }
            }
        }
    }

    static void initTower() {
        towerList = new ArrayList<>();
        prev = new Point[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] != null) {
                    board[i][j].relatedAttack = false;
                    towerList.add(board[i][j]);
                }
            }
        }

        Collections.sort(towerList);
    }

    // 남아있는 포탑들 중 "공격력"이 가장 강한 포탑.
    static int findAnswer() {
        int power = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (board[i][j] != null) {
                    power = Math.max(power, board[i][j].power);
                }
            }
        }

        return power;
    }
}
