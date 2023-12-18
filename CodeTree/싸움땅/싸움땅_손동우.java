import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    private static final int NONE = -1;

    // ↑, →, ↓, ←
    private static final int[] dy = {-1, 0, 1, 0};
    private static final int[] dx = {0, 1, 0, -1};

    private static int N, M, K;
    private static PriorityQueue<Integer>[][] guns;
    private static int[][] playerMap;
    private static Player[] players;

    public static void main(String[] args) throws IOException {
        init();
        for (int i = 0; i < K; i++) {
            play();
        }
        printScore();
    }

    private static void play() {
        for (Player player1 : players) {
            movePlayer(player1);
            if (playerMap[player1.y][player1.x] != NONE) {
                Player player2 = players[playerMap[player1.y][player1.x]];
                fight(player1, player2);
            } else {
                playerMap[player1.y][player1.x] = player1.id;
                getNewGun(player1);
            }
        }
    }

    private static void movePlayer(Player player) {
        playerMap[player.y][player.x] = NONE;
        while (true) {
            int ny = player.y + dy[player.direction];
            int nx = player.x + dx[player.direction];
            if (isInMap(ny, nx)) {
                player.y = ny;
                player.x = nx;
                break;
            } else {
                player.direction = (player.direction + 2) % 4;
            }
        }
    }

    private static void fight(Player player1, Player player2) {
        if (player1.getTotalStat() > player2.getTotalStat()) {
            victory(player1, player2);
            lose(player2);
            getNewGun(player1);
        } else if (player1.getTotalStat() < player2.getTotalStat()) {
            victory(player2, player1);
            lose(player1);
            getNewGun(player2);
        } else if (player1.stat > player2.stat) {
            victory(player1, player2);
            lose(player2);
            getNewGun(player1);
        } else if (player1.stat < player2.stat) {
            victory(player2, player1);
            lose(player1);
            getNewGun(player2);
        }
    }

    private static void lose(Player loser) {
        putGunDown(loser);
        while (true) {
            int ny = loser.y + dy[loser.direction];
            int nx = loser.x + dx[loser.direction];
            if (isInMap(ny, nx) && playerMap[ny][nx] == NONE) {
                loser.y = ny;
                loser.x = nx;
                if (!guns[ny][nx].isEmpty()) {
                    loser.gun = guns[ny][nx].poll();
                }
                playerMap[ny][nx] = loser.id;
                break;
            } else {
                loser.direction = (loser.direction + 1) % 4;
            }
        }
    }

    private static void victory(Player winner, Player loser) {
        winner.score += winner.getTotalStat() - loser.getTotalStat();
        playerMap[winner.y][winner.x] = winner.id;
    }

    private static boolean isInMap(int y, int x) {
        return 0 <= y && y < N && 0 <= x && x < N;
    }

    private static void getNewGun(Player player) {
        putGunDown(player);
        if (!guns[player.y][player.x].isEmpty()) {
            player.gun = guns[player.y][player.x].poll();
        }
    }

    private static void putGunDown(Player player) {
        if (player.hasGun()) {
            guns[player.y][player.x].offer(player.gun);
            player.gun = 0;
        }
    }

    private static void printScore() {
        Arrays.stream(players).mapToInt(player -> player.score).forEach(score -> System.out.print(score + " "));
    }

    private static void init() throws IOException {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        guns = new PriorityQueue[N][N];
        playerMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(playerMap[i], NONE);
            for (int j = 0; j < N; j++) {
                guns[i][j] = new PriorityQueue<>(Comparator.reverseOrder());
            }
        }
        players = new Player[M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                guns[i][j].offer(Integer.parseInt(st.nextToken()));
            }
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int direction = Integer.parseInt(st.nextToken());
            int stat = Integer.parseInt(st.nextToken());
            players[i] = new Player(i, y - 1, x - 1, direction, stat);
            playerMap[players[i].y][players[i].x] = i;
        }
    }

    private static class Player {
        int id;
        int y;
        int x;
        int direction;
        int stat;
        int gun;
        int score;

        public Player(int id, int y, int x, int direction, int stat) {
            this.id = id;
            this.y = y;
            this.x = x;
            this.direction = direction;
            this.stat = stat;
        }

        public int getTotalStat() {
            return stat + gun;
        }

        public boolean hasGun() {
            return gun > 0;
        }
    }
}