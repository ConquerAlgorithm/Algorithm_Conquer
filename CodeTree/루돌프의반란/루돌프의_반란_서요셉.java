package CodeTree.삼성스터디.루돌프의_반란;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * - 개체
 *  1. 격자
 *      - N * N 크기 격자
 *      - 좌상단 (1, 1)
 *      - M 개의 턴에 걸쳐 진행. 루돌프와 산타가 한번씩 움직인다. 루돌프가 한번 움직이고 1번부터 P번 산타까지 순서대로 움직인다.
 *      - 이때 기절했거나 격자 밖으로 나가 탈락한 산타는 움직일 수 없다.
 *      - 두칸 사이의 거리는 (x1 - x2) ^ 2 + (y1 - y2) ^ 2로 계산된다.
 *
 * - 진행
 *  1. 루돌프의 움직임
 *      - 가장 가까운 산타를 향해 1칸 돌진. 단, 탈락하지 않은 산타 선택
 *      - 만약 가장 가까운 산타가 2명 이상이면 r 좌표가 큰 산타 선택. r이 동일하면 c 좌표가 큰 산타 선택
 *      - 상하좌우대각선 8방 중 하나로 이동 가능. 대각선 방향 전진도 1칸 전진으로 생각해야함.
 *      - 가장 우선순위가 높은 산타를 향해 8방중 가장 가까워지는 방향으로 한칸 돌진.
 *
 *  2. 산타 움직임
 *      - 1번부터 P번 까지 순서대로 움직임
 *      - 기절했거나 탈락한 산타는 움직일 수 없음
 *      - 산타는 루돌프에게 거리가 가장 가까워지는 방향으로 1칸 이동
 *      - 산타는 다른 산타가 있는 칸이나 격자 밖으로 움직이지 않는다.
 *      - 움직일 수 있는 칸이 없으면 움직이지 않는다.
 *      - 움직일 수 있는 칸이 있더라도 루돌프와 가까워질 수 있는 방법이 없다면 움직이지 않는다.
 *      - 산타는 상하좌우 4방 중 하나로 이동 가능. 이때 가장 가까워질 수 있는 방향이 여러개라면 상우하좌 우선순위.
 *
 *  3. 충돌
 *      - 산타, 루돌프가 같은 칸이면 충돌한다.
 *      - 루돌프의 이동으로 충돌이 일어나면 해당 산타는 C 만큼의 점수를 얻는다. 이와 동시에 산타는 루돌프가 이동해온 방향으로 C칸 만큼 밀려난다.
 *      - 산타의 이동으로 충돌이 일어나면 해당 산타는 D 만큼의 점수를 얻는다. 이와 동시에 산타는 자신이 이동해온 반대 방향으로 D칸 만큼 밀려난다.
 *      - 밀려나는 것은 도중에 충돌이 일어나지 않고 정확히 원하는 위치에 도달한다.
 *      - 만약 밀려난 위치가 격자 밖에면 산타는 탈락한다.
 *      - 밀려난 칸에 다른 산타가 있는 경우 4. 상호작용이 일어난다.
 *
 *  4. 상호작용
 *      - 충돌 후 착지 칸에 다른 산타가 있으면 그 산타는 1칸 해당 방향으로 밀려난다. 그 옆에 있다면 연쇄적으로 1칸씩 밀려난다.
 *      - 격자 밖으로 나간 산타는 탈락된다.
 *
 *  5. 기절
 *      - 루돌프와 충돌하면 기절한다. 현재가 k 번째 턴이면 (k + 1)번째 턴까지 기절하여 (k + 2) 번째 턴부터 정상이 된다.
 *      - 기절한 산타는 움직일 수 없다.
 *      - 루돌프는 기절한 산타를 돌진 대상으로 선택할 수 있다.
 *
 *  6. 게임 종료
 *      - M 번의 턴에 걸쳐 루돌프 산타가 순서대로 움직인 후 게임이 종료됨.
 *      - 만약 P 명의 산타가 모두 탈락하면 즉시 게임이 종료된다.
 *      - 매 턴 이후 탈락하지 않은 산타는 1점씩 부여된다.
 *
 * - 정답 : 각 산타가 얻은 최종 점수를 구하라.
 */
public class 루돌프의_반란 {

    static final int M_RUDOLF = 31;
    static final int M_SANTA = 2;

    static class Point {
        int x, y, dist, dir;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void calDist() {
            this.dist = (int)Math.pow(rudolf.x - this.x, 2) + (int) Math.pow(rudolf.y - this.y, 2);
        }
    }

    static class Rudolf extends Point {
        public Rudolf(int x, int y) {
            super(x, y);
            setPoint();
        }

        public void setPoint() {
            board[x][y] = M_RUDOLF;
        }

        public void setBlack() {
            board[x][y] = 0;
        }
    }

    static class Santa extends Point implements Comparable<Santa> {
        boolean isStunned, isFailed;
        int id, dist, score, turn;

        public Santa(int x, int y, int id) {
            super(x, y);
            this.id = id;
            setPoint();
        }

        public void setPoint() {
            board[x][y] = id;
        }

        public void setBlack() {
            board[x][y] = 0;
        }

        @Override
        public String toString() {
            return id + "번 Santa 좌표 : (" + x + ", " + y +
                    "), 기절=" + isStunned +
                    ", 탈락=" + isFailed +
                    ", 루돌프와거리=" + dist +
                    ", 점수=" + score +
                    ", 기절풀리는턴=" + turn +
                    '}';
        }

        @Override
        public int compareTo(Santa s) {
            if (this.dist != s.dist) return this.dist - s.dist;
            if (this.x != s.x) return s.x - this.x;
            return s.y - this.y;
        }
    }

    static int N, M, P, C, D;

    static Rudolf rudolf;
    static int[][] board;
    static Santa[] santaPool;
    static HashMap<Integer, Santa> livedSantaHM;

    // 상 우상 우 우하 하 좌하 좌 좌상
    static int[] rdx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] rdy = {0, 1, 1, 1, 0, -1, -1, -1};

    // 상 우 하 좌
    static int[] sdx = {-1, 0, 1, 0};
    static int[] sdy = {0, 1, 0, -1};

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        board = new int[N + 1][N + 1];
        santaPool = new Santa[P + 1];
        livedSantaHM = new HashMap<>();

        st = new StringTokenizer(br.readLine());
        rudolf = new Rudolf(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));


        for (int i = 0; i < P; i++) {
            st = new StringTokenizer(br.readLine());

            int id = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            Santa newSanta = new Santa(x, y, id);
            santaPool[id] = newSanta;
            livedSantaHM.put(id, newSanta);
        }
    }

    public static void main(String[] args) throws Exception {
        input();

        // M 번의 턴 진행
        for (int t = 1; t <= M; t++) {
            turnOver(t);

            // 루돌프 이동
            moveRudolf(t);
            calDist();

            // 산타 이동
            for (int i = 1; i <= P; i++) {
                Santa santa = santaPool[i];
                if (!santa.isStunned && !santa.isFailed) moveSanta(santa, t);
            }

            // 탈락하지 않은 산타 1점씩 부여
            if (livedSantaHM.size() == 0) break;
            else {
                for (Santa santa : livedSantaHM.values()) {
                    santa.score++;
                }
            }
        }

        printAnswer();
    }

    static void moveSanta(Santa santa, int turn) {
        // 루돌프로 향하는 방향 설정
        int dir = findDir(santa, rudolf, M_SANTA, santa.id);

        if (dir == -1) return;

        int nx = santa.x + sdx[dir];
        int ny = santa.y + sdy[dir];

        santa.setBlack();
        santa.x = nx;
        santa.y = ny;

        // 만일 산타가 이동활 좌표에 루돌프가 있다면 충돌 발생
        if (board[nx][ny] == M_RUDOLF) {
            crash(santa, dir, M_SANTA, turn);
        } else {
            // 산타 새 좌표로 이동처리
            santa.setPoint();
        }
    }

    static void moveRudolf(int turn) {
        // 루돌프와 모든 산타들간의 거리를 측정한다.
        calDist();

        // 루돌프가 조건 우선순위 산타를 선정하기 위한 PQ
        PriorityQueue<Santa> pq = new PriorityQueue<>();
        pq.addAll(livedSantaHM.values());

        // 루돌프의 타겟 산타
        Santa targetSanta = pq.poll();

        // 타겟 산타로 향하는 방향 설정
        int dir = findDir(rudolf, targetSanta, M_RUDOLF, targetSanta.id);

        int nx = rudolf.x + rdx[dir];
        int ny = rudolf.y + rdy[dir];

        rudolf.setBlack();

        // 만일 루돌프가 이동할 좌표에 산타가 있다면 충돌 발생
        if (board[nx][ny] != 0) {
            crash(targetSanta, dir, M_RUDOLF, turn);
        }

        // 루돌프 새 좌표로 이동처리
        rudolf.x = nx;
        rudolf.y = ny;
        rudolf.setPoint();
    }

    static void crash(Santa santa, int dir, int mOption, int turn) {
        if (mOption == M_RUDOLF) {
            // 해당 좌표의 산타는 C 만큼의 점수를 얻고 해당 방향으로 C칸 밀려난다.
            santa.score += C;

            int nx = santa.x + rdx[dir] * C;
            int ny = santa.y + rdy[dir] * C;

            if (isNotBoundary(nx, ny)) {
                santa.isFailed = true;
                livedSantaHM.remove(santa.id);
            } else {
                santa.isStunned = true;
                santa.turn = turn + 2;

                if (board[nx][ny] != 0) push(nx, ny, dir, mOption);

                santa.x = nx;
                santa.y = ny;
                santa.setPoint();
            }
        } else if (mOption == M_SANTA) {
            // 이동된 산타는 D 만큼의 점수를 얻고 주어진 방향의 반대 방향으로 D 칸 밀려난다.
            santa.score += D;

            int nDir = (dir + 2) % 4;
            int nx = santa.x + sdx[nDir] * D;
            int ny = santa.y + sdy[nDir] * D;

            if (isNotBoundary(nx, ny)) {
                santa.isFailed = true;
                livedSantaHM.remove(santa.id);
            } else {
                santa.isStunned = true;
                santa.turn = turn + 2;

                if (board[nx][ny] != 0) push(nx, ny, nDir, mOption);

                // 산타 새 좌표로 이동처리
                santa.x = nx;
                santa.y = ny;
                santa.setPoint();
            }

        }
    }

    static void push(int x, int y, int dir, int mOption) {
        Santa santa = livedSantaHM.get(board[x][y]);

        int nx = 0;
        int ny = 0;

        if (mOption == M_RUDOLF) {
            nx = santa.x + rdx[dir];
            ny = santa.y + rdy[dir];
        } else if (mOption == M_SANTA) {
            nx = santa.x + sdx[dir];
            ny = santa.y + sdy[dir];
        }

        if (isNotBoundary(nx, ny)) {
            santa.isFailed = true;
            livedSantaHM.remove(santa.id);
        } else {
            if (board[nx][ny] != 0) push(nx, ny, dir, mOption);

            santa.x = nx;
            santa.y = ny;
            santa.setPoint();
        }
    }

    static boolean isNotBoundary(int x, int y) {
        return !(1 <= x && x <= N && 1 <= y && y <= N);
    }

    static void calDist() {
        for (Santa s: livedSantaHM.values()) {
            s.dist = (int)Math.pow(rudolf.x - s.x, 2) + (int)Math.pow(rudolf.y - s.y, 2);
        }
    }

    static int findDir(Point from, Point to, int mOption, int santaId) {
        int dir = -1;

        if (mOption == M_RUDOLF) {
            // 상
            if (from.x > to.x && from.y == to.y) dir = 0;
                // 우상
            else if (from.x > to.x && from.y < to.y) dir = 1;
                // 우
            else if (from.x == to.x && from.y < to.y) dir = 2;
                // 우하
            else if (from.x < to.x && from.y < to.y) dir = 3;
                // 하
            else if (from.x < to.x && from.y == to.y) dir = 4;
                // 좌하
            else if (from.x < to.x && from.y > to.y) dir = 5;
                // 좌
            else if (from.x == to.x && from.y > to.y) dir = 6;
                // 좌상
            else if (from.x > to.x && from.y > to.y) dir = 7;

        } else if (mOption == M_SANTA){
            PriorityQueue<Point> pq = new PriorityQueue<Point>((p1, p2) -> {return p1.dist - p2.dist;});

            for (int d = 0; d < 4; d++) {
                int nx = from.x + sdx[d];
                int ny = from.y + sdy[d];

                // 격자 밖이거나 다른 산타가 있다면 이동 불가
                if (isNotBoundary(nx, ny) || board[nx][ny] != M_RUDOLF && board[nx][ny] != 0) continue;

                Point checkPoint = new Point(nx, ny);
                checkPoint.calDist();
                checkPoint.dir = d;
                pq.add(checkPoint);
            }

            while (!pq.isEmpty()) {
                // 이동하려는 지점과 루돌프와의 거리가 현재 위치 거리보다 크면 뽑아낸다.
                int dist = pq.peek().dist;

                if (dist > santaPool[santaId].dist) pq.poll();
                else {
                    dir = pq.poll().dir;
                    break;
                }
            }
        }

        return dir;
    }

    static void turnOver(int turn) {
        // 기절해있는데 해당 턴과 턴이 같은 산타들 기절 풀리기
        for (Santa s: livedSantaHM.values()) {
            if (s.isStunned && s.turn == turn) s.isStunned = false;
        }
    }

    static void printAnswer() {
        for (int i = 1; i <= P; i++) {
            System.out.print(santaPool[i].score + " ");
        }
    }
}
