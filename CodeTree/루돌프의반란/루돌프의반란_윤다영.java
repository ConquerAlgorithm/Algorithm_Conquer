import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static int N, M, P, C, D;
    private static Map<Integer, Santa> santaList;
    private static int[][] map;
    private static Rudolf rudolf;
    private static final int[] rudolfDx = {0, 1, 1, 1, 0, -1, -1, -1};
    private static final int[] rudolfDy = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static final int[] santaDx = {0, 1, 0, -1};
    private static final int[] santaDy = {-1, 0, 1, 0};

    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        init();

        while (M-- > 0) {
            moveRudolf();
            moveSanta();
            scoreUp();
        }

        for (Santa santa : santaList.values()) {
            System.out.print(santa.score + " ");
        }
    }

    private static void scoreUp() {
        for (int key : santaList.keySet()) {
            Santa santa = santaList.get(key);
            if (santa.isAlive)
                santa.score += 1;
        }
    }

    private static void moveSanta() {
        for (int key : santaList.keySet()) {
            Santa santa = santaList.get(key);
            if (santa.stunCnt > 0 || !santa.isAlive) {
                santa.stunCnt--;
                continue;
            }
            int minDis = calDis(santa.x, santa.y, rudolf.x, rudolf.y), dis = 0;
            for (int i = 0; i < 4; i++) {
                int nx = santa.x + santaDx[i];
                int ny = santa.y + santaDy[i];
                if (outRange(nx, ny) || (map[ny][nx] != 0 && map[ny][nx] != 40))
                    continue;
                if (minDis > calDis(nx, ny, rudolf.x, rudolf.y)) {
                    dis = i;
                    minDis = calDis(nx, ny, rudolf.x, rudolf.y);
                }
            }
            if(minDis == calDis(santa.x, santa.y, rudolf.x, rudolf.y)) continue;

            map[santa.y][santa.x] = 0;
            santa.x += santaDx[dis];
            santa.y += santaDy[dis];
            if (map[santa.y][santa.x] == 40)
                conflict(false, (dis + 2) % 4, key);
            else
                map[santa.y][santa.x] = key;
        }
    }

    private static boolean outRange(int x, int y) {
        return x >= N || y >= N || x < 0 || y < 0;
    }

    private static void moveRudolf() {
        Santa moveSanta = null;
        int minDis = Integer.MAX_VALUE;
        map[rudolf.y][rudolf.x] = 0;
        for (int n : santaList.keySet()) {
            Santa santa = santaList.get(n);
            if (!santa.isAlive)
                continue;
            int dis = calDis(santa.x, santa.y, rudolf.x, rudolf.y);
            if (dis < minDis) {
                moveSanta = santa;
                minDis = dis;
            } else if (dis == minDis && moveSanta != null) {
                if (moveSanta.y < santa.y)
                    moveSanta = santa;
                else if (moveSanta.y == santa.y)
                    if (moveSanta.x < santa.x)
                        moveSanta = santa;
            }
        }
        if (moveSanta == null)
            return;

        int disIdx = 0;
        minDis = Integer.MAX_VALUE;
        for (int i = 0; i < 8; i++) {
            int nx = rudolf.x + rudolfDx[i];
            int ny = rudolf.y + rudolfDy[i];
            if (outRange(nx, ny))
                continue;
            if (minDis > calDis(nx, ny, moveSanta.x, moveSanta.y)) {
                minDis = calDis(nx, ny, moveSanta.x, moveSanta.y);
                disIdx = i;
            }
        }

        rudolf.x += rudolfDx[disIdx];
        rudolf.y += rudolfDy[disIdx];
        map[rudolf.y][rudolf.x] = 40;

        conflict(true, disIdx, 0);
    }

    // k == 0 이면 루돌프가 먼저 옴
    private static void conflict(boolean isRudolf, int disIdx, int k) {
        Santa santa = null;
        if (k == 0) {
            for (int key : santaList.keySet()) {
                Santa obj = santaList.get(key);
                if (!obj.isAlive)
                    continue;
                if (obj.x == rudolf.x && obj.y == rudolf.y) {
                    santa = obj;
                    k = key;
                }
            }
        } else
            santa = santaList.get(k);

        if (santa == null)
            return;

        int num = D;
        if (isRudolf) {
            num = C;
            santa.stunCnt = 2;
            for (int i = 0; i < num; i++) {
                santa.x += rudolfDx[disIdx];
                santa.y += rudolfDy[disIdx];
            }
        } else {
            santa.stunCnt = 1;
            for (int i = 0; i < num; i++) {
                santa.x += santaDx[disIdx];
                santa.y += santaDy[disIdx];
            }
        }
        santa.score += num;

        if (outRange(santa.x, santa.y)) {
            santa.isAlive = false;
            return;
        }
        map[santa.y][santa.x] = k;

        for (int key : santaList.keySet()) {
            Santa conflictSanta = santaList.get(key);
            if (key != k && santa.x == conflictSanta.x && santa.y == conflictSanta.y)
                santaConflict(isRudolf, disIdx, key);
        }
    }

    private static void santaConflict(boolean isRudolf, int disIdx, int key) {
        Santa santa = santaList.get(key);
        if (isRudolf) {
            santa.x += rudolfDx[disIdx];
            santa.y += rudolfDy[disIdx];

        } else {
            santa.x += santaDx[disIdx];
            santa.y += santaDy[disIdx];
        }
        if (outRange(santa.x, santa.y)) {
            santa.isAlive = false;
            return;
        }
        map[santa.y][santa.x] = key;
        for (int k : santaList.keySet()) {
            Santa conflictSanta = santaList.get(k);
            if (key != k && santa.x == conflictSanta.x && santa.y == conflictSanta.y)
                santaConflict(isRudolf, disIdx, k);
        }
    }

    private static int calDis(int x1, int y1, int x2, int y2) {
        return (int)(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static void init() {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        P = sc.nextInt();
        C = sc.nextInt();
        D = sc.nextInt();
        rudolf = new Rudolf(sc.nextInt() - 1, sc.nextInt() - 1);
        santaList = new HashMap<>();
        for (int i = 0; i < P; i++) {
            santaList.put(sc.nextInt(), new Santa(sc.nextInt() - 1, sc.nextInt() - 1));
        }
        map = new int[N][N];
        map[rudolf.y][rudolf.x] = 40;
        for (Map.Entry<Integer, Santa> entry : santaList.entrySet()) {
            map[entry.getValue().y][entry.getValue().x] = entry.getKey();
        }
    }
}

class Rudolf {
    int x;
    int y;

    public Rudolf(int y, int x) {
        this.x = x;
        this.y = y;
    }
}

class Santa {
    int x;
    int y;
    boolean isAlive;
    int stunCnt;
    int score;

    public Santa(int y, int x) {
        this.x = x;
        this.y = y;
        isAlive = true;
        stunCnt = 0;
        score = 0;
    }
}