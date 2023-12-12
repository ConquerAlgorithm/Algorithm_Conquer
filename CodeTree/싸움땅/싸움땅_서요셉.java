package CodeTree.삼성스터디.싸움땅;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 싸움땅 {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Player extends Point {
        int idx, dir, power, gun, score;

        public Player(int x, int y, int idx, int dir, int power) {
            super(x, y);
            this.idx = idx;
            this.dir = dir;
            this.power = power;
            this.gun = 0;
            this.score = 0;
        }

        @Override
        public String toString() {
            return "[" + idx + "] P 좌표 =(" + x + ", " + y +
                    "), dir=" + dir +
                    ", power=" + power +
                    ", gun=" + gun +
                    ", score=" + score +
                    '}';
        }
    }

    static int N, M, K;
    static List<Integer>[][] gunBoard;
    static int[][] playerBoard;
    static List<Player> playerList;

    // 90도 회전을 위해 순서 배치 - 상 우 하 좌
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        gunBoard = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                gunBoard[i][j] = new ArrayList<>();
            }
        }
        playerBoard = new int[N][N];
        playerList = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int gun = Integer.parseInt(st.nextToken());

                if (gun != 0) gunBoard[i][j].add(gun);
            }
        }

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());

            Player player = new Player(x, y, i, d, p);
            playerBoard[x][y] = i;
            playerList.add(player);
        }
    }

    public static void main(String[] args) throws Exception {
        input();

        for (int i = 1; i <= K; i++) {
            // 모든 플레이어 이동
            for (Player p: playerList) {
                movePlayer(p);
            }
        }

        // 각 플레이어들이 획득한 포인트를 공백을 두고 출력
        printAnswer();
    }

    static void movePlayer(Player p1) {
        // p1 이 향하고 있는 방향대로 한칸 이동한다.
        // 격자를 벗어나게 된다면 방향을 바꿔 한칸 이동한다.
        int cx = p1.x;
        int cy = p1.y;

        int nx = cx + dx[p1.dir];
        int ny = cy + dy[p1.dir];

        if (isNotBoundary(nx, ny)) {
            p1.dir = (p1.dir + 2) % 4;

            nx = cx + dx[p1.dir];
            ny = cy + dy[p1.dir];
        }

        // 플레이어 실제 이동 처리 (리스트에만 해두고 playerBoard 에는 아직 새로 갱신하지 않는다.)
        playerBoard[p1.x][p1.y] = 0;
        p1.x = nx;
        p1.y = ny;

        // 이동한 방향에 플레이어가 있다면 전투를 한다.
        if (playerBoard[p1.x][p1.y] != 0) {
            Player p2 = playerList.get(playerBoard[p1.x][p1.y] - 1);
            battle(p1, p2);
        }

        // 이동한 방향에 플레이어가 없다면 총이 있는지 확인한다.
        // 총이 있다면 해당 플레이어는 가장 센 총을 획득하고 기존에 들고 있는 총은 내려놓는다.
        else {
            playerBoard[p1.x][p1.y] = p1.idx;
            if (!gunBoard[p1.x][p1.y].isEmpty()) getGun(p1);
        }

    }

    static void battle(Player p1, Player p2) {
        // 각 플레이어들의 초기능력치 + 총 의 합의 대소로 큰 플레이어가 이긴다.
        // 수치가 같다면 초기능력치가 높은 플레이어가 이긴다.
        // 이긴 플레이어는 각 플레이어의 초기능력치와 총 공격력 합의 차이만큼 점수를 획득
        Player win = null;
        Player lose = null;

        int p1_all = p1.power + p1.gun;
        int p2_all = p2.power + p2.gun;

        if (p1_all > p2_all) {
            win = p1;
            lose = p2;
        } else if (p1_all < p2_all) {
            win = p2;
            lose = p1;
        } else {
            if (p1.power > p2.power) {
                win = p1;
                lose = p2;
            } else if (p1.power < p2.power) {
                win = p2;
                lose = p1;
            }
        }

        // 진 플레이어는 총을 격자에 내려놓고 원래 방향대로 한 칸 이동한다.
        // 이동하려는 칸에 플레이어가 있거나 격자 범위 밖이면 오른쪽으로 90도 회전하여 빈칸이 보이면 바로 이동
        // 이동하려는 칸에 총이 있다면 센 총을 획득하고 기존에 들고 있는 총은 격자에 내려놓는다.
        moveLoser(lose);

        // 이긴 플레이어는 칸에 떨어져있는 총들과 원래 들고있던 총 중 가장 센것을 획득.
        win.score += Math.abs(p1_all - p2_all);
        playerBoard[win.x][win.y] = win.idx;
        if (!gunBoard[win.x][win.y].isEmpty()) getGun(win);

    }

    static void moveLoser(Player p) {
        int cx = p.x;
        int cy = p.y;

        if (p.gun != 0) {
            gunBoard[cx][cy].add(p.gun);
            p.gun = 0;
        }

        int nx = cx + dx[p.dir];
        int ny = cy + dy[p.dir];

        while (!canMove(nx, ny)) {
            p.dir = (p.dir + 1) % 4;

            nx = cx + dx[p.dir];
            ny = cy + dy[p.dir];
        }

        p.x = nx;
        p.y = ny;
        playerBoard[p.x][p.y] = p.idx;

        if (!gunBoard[p.x][p.y].isEmpty()) getGun(p);
    }

    static void getGun(Player p) {
        // 총이 있다면 해당 플레이어는 가장 센 총을 획득하고 기존에 들고 있는 총은 내려놓는다.
        if (p.gun != 0) {
            gunBoard[p.x][p.y].add(p.gun);
            p.gun = 0;
        }

        int max = 0;
        int maxIdx = 0;

        for (int i = 0; i < gunBoard[p.x][p.y].size(); i++) {
            int gun = gunBoard[p.x][p.y].get(i);

            if (max < gun) {
                max = gun;
                maxIdx = i;
            }
        }

        p.gun = max;
        gunBoard[p.x][p.y].remove(maxIdx);
    }

    static boolean canMove(int x, int y) {
        return !isNotBoundary(x, y) && playerBoard[x][y] == 0;
    }

    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < N);
    }

    static void printAnswer() {
        for (Player p: playerList) {
            System.out.print(p.score + " ");
        }
    }
}
