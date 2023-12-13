
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    static int n, m, k;
    static PriorityQueue<Integer>[][] map;
    static int[][] playerMap;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0 ,-1};
    static List<Player> players = new ArrayList<>();

    public static void main(String[] args) {
        init();
        print();
        while(k-- > 0) {
            move();
            print();
        }
        for(Player player : players) {
            System.out.print(player.score + " ");
        }
    }
    private static void print() {
        System.out.println("map:");
        for(int[] arr : playerMap) {
            for(int i : arr) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        System.out.println("atk: ");
        for(Player player : players) {
            System.out.print(player.atk + " ");
        }
        System.out.println();

        System.out.println("gun: ");
        for(Player player : players) {
            System.out.print(player.gun + " ");
        }
        System.out.println();

        System.out.println("score: ");
        for(Player player : players) {
            System.out.print(player.score + " ");
        }
        System.out.println();
    }
    private static boolean outRange(int x, int y) {
        return x < 0 || x >= n || y < 0 || y >= n;
    }
    private static void move() {
        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int nx = player.pair.x + dx[player.dir];
            int ny = player.pair.y + dy[player.dir];
            if(outRange(nx, ny)) {
                nx = player.pair.x + dx[(player.dir + 2) % 4];
                ny = player.pair.y + dy[(player.dir + 2) % 4];
                player.dir = (player.dir + 2) % 4;
            }

            playerMap[player.pair.x][player.pair.y] = 0;
            player.pair.x = nx;
            player.pair.y = ny;
            if(playerMap[nx][ny] != 0) {
                Player attackPlayer = players.get(playerMap[nx][ny] - 1);
                attack(player, attackPlayer);
            } else {
                changeGun(player);
                playerMap[nx][ny] = player.id;
            }

        }
    }

    private static void changeGun(Player player) {
        if(map[player.pair.x][player.pair.y].isEmpty()) return;
        int gun = map[player.pair.x][player.pair.y].poll();
        int temp1 = Math.max(gun, player.gun);
        int temp2 = Math.min(gun, player.gun);
        player.gun = temp1;
        if(temp2 != 0) map[player.pair.x][player.pair.y].add(temp2);
    }

    private static void attack(Player player1, Player player2) {
        int player1Atk = player1.gun + player1.atk;
        int player2Atk = player2.gun + player2.atk;
        // player1이 이김
        // 이길 때, 총을 바꾸는 로직 추가
        if(player1Atk > player2Atk) {
            player1.score += player1Atk - player2Atk;
            playerMap[player1.pair.x][player1.pair.y] = player1.id;
            loose(player2);
            changeGun(player1);
        } else if(player1Atk < player2Atk) {
            player2.score += player2Atk - player1Atk;
            playerMap[player2.pair.x][player2.pair.y] = player2.id;
            loose(player1);
            changeGun(player2);

        } else {

            if(player1.atk > player2.atk){
                loose(player2);
                playerMap[player1.pair.x][player1.pair.y] = player1.id;
                changeGun(player1);
            }
            else {
                loose(player1);
                playerMap[player2.pair.x][player2.pair.y] = player2.id;
                changeGun(player2);
            }
        }
    }

    private static void loose(Player player) {
        if(player.gun != 0) {
            map[player.pair.x][player.pair.y].add(player.gun);
            player.gun = 0;
        }

        int nx = player.pair.x + dx[player.dir];
        int ny = player.pair.y + dy[player.dir];

        // 90도 회전
        if(outRange(nx, ny) || playerMap[nx][ny] != 0) {
            for(int i = 1; i < 4; i++) {
                nx = player.pair.x + dx[(player.dir + i) % 4];
                ny = player.pair.y + dy[(player.dir + i) % 4];
                if(outRange(nx, ny) || playerMap[nx][ny] != 0) continue;
                player.dir = (player.dir + i) % 4;
                break;
            }
        } else {
            nx = player.pair.x + dx[player.dir];
            ny = player.pair.y + dy[player.dir];
            if(outRange(nx, ny)) {
                nx = player.pair.x + dx[(player.dir + 2) % 4];
                ny = player.pair.y + dy[(player.dir + 2) % 4];
            }
        }
        player.pair.x = nx;
        player.pair.y = ny;
        playerMap[nx][ny] = player.id;
    }

    private static void init() {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        k = sc.nextInt();
        map = new PriorityQueue[n][n];
        playerMap = new int[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                map[i][j] = new PriorityQueue<>(Collections.reverseOrder());
                map[i][j].add(sc.nextInt());
            }
        }

        for(int i = 0; i < m; i++) {
            int x = sc.nextInt() - 1;
            int y = sc.nextInt() - 1;
            int dir = sc.nextInt();
            int atk = sc.nextInt();
            int gun = 0;
            playerMap[x][y] = i + 1;
            players.add(new Player(i + 1, new Pair(x, y), dir, atk, gun));
        }
    }
}

class Pair {
    int x;
    int y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Player {
    int id;
    Pair pair;
    int dir;
    int atk;
    int gun;
    int score = 0;

    public Player(int id, Pair pair, int dir, int atk, int gun) {
        this.id = id;
        this.pair = pair;
        this.dir = dir;
        this.atk = atk;
        this.gun = gun;
    }
}