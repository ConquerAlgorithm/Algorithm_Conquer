import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main{

    static class Turret implements Comparable<Turret> {
        int x;
        int y;
        int atk;
        int atkCnt;

        public Turret(int x, int y, int atk, int atkCnt) {
            this.x = x;
            this.y = y;
            this.atk = atk;
            this.atkCnt = atkCnt;
        }

        @Override
        public int compareTo(Turret o) {
            if(this.atk != o.atk) return this.atk - o.atk;
            if(this.atkCnt != o.atkCnt) return o.atkCnt - this.atkCnt;
            if(this.x + this.y != o.x + o.y) return (o.x + o.y) - (this.x + this.y);
            return o.y - this.y;
        }
    }
    static int N, M, K;
    static int[][] map, attack;
    static List<Turret> turretList = new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        K = sc.nextInt();
        map = new int[N][M];
        attack = new int[N][M];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j< M; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        while(K-- > 0) {
            init();
            // 공격자 선정
            Turret atkTurret = turretList.get(0);
            atkTurret.atkCnt = 1;
            attack[atkTurret.y][atkTurret.x] = atkTurret.atkCnt;
            atkTurret.atk += N + M;
            map[atkTurret.y][atkTurret.x] = atkTurret.atk;
            if(!laser(atkTurret)){
                bomb(atkTurret);
            }

            for(int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++) {
                    if(map[i][j] < 6000 && map[i][j] > 0 && (atkTurret.x != j || atkTurret.y != i)) {
                        map[i][j]++;
                    } else if(map[i][j] >= 6000) map[i][j] -= 6000;
                }
            }
        }
        init();
        System.out.println(turretList.get(turretList.size() -1).atk);
    }

    private static void bomb(Turret atkTurret) {
        Turret turret = turretList.get(turretList.size() - 1);
        int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};
        int x = turret.x;
        int y = turret.y;
        map[y][x] = Math.max(map[y][x] - atkTurret.atk, 0);
        map[y][x] += 6000;
        for(int i = 0; i < 8; i++) {
            int nx = (x + dx[i] + M) % M;
            int ny = (y + dy[i] + N) % N;
            if(map[ny][nx] == 0) continue;
            map[ny][nx] = Math.max(map[ny][nx] - (atkTurret.atk / 2), 0);
            map[ny][nx] += 6000;
        }
    }

    private static boolean laser(Turret atkTurret) {
        Turret turret = turretList.get(turretList.size() - 1);

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{turret.x, turret.y});
        boolean[][] visited = new boolean[N][M];
        int[][] check = new int[N][M];
        visited[turret.y][turret.x] = true;
        int[] dx = new int[]{1, 0, -1, 0};
        int[] dy = new int[]{0, 1, 0, -1};

        while(!queue.isEmpty()) {
            int[] pair = queue.poll();
            if(pair[0] == atkTurret.x && pair[1] == atkTurret.y) {
                break;
            }
            for(int i = 0; i < 4; i++) {
                int nx = (pair[0] + dx[i] + M) % M;
                int ny = (pair[1] + dy[i] + N) % N;
                if(visited[ny][nx] || map[ny][nx] == 0) continue;
                visited[ny][nx] = true;
                check[ny][nx] = check[pair[1]][pair[0]] + 1;
                queue.add(new int[]{nx, ny});
            }
        }
        // 공격자가 공격할 수 있음
        int dis = check[atkTurret.y][atkTurret.x];
        if(dis != 0) {
            int x = atkTurret.x;
            int y = atkTurret.y;
            while(x != turret.x || y != turret.y) {
                dis--;
                for(int i = 0; i < 4; i++) {
                    int nx = (x + dx[i] + M) % M;
                    int ny = (y + dy[i] + N) % N;
                    if(!visited[ny][nx] || check[ny][nx] != dis || map[ny][nx] == 0) continue;
                    x = nx;
                    y = ny;
                    if(nx == turret.x && ny == turret.y) {
                        map[ny][nx] = Math.max(map[ny][nx] - atkTurret.atk, 0);
                    } else
                        map[ny][nx] = Math.max(map[ny][nx] - (atkTurret.atk / 2), 0);
                    map[ny][nx] += 6000;
                    break;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static void init() {
        turretList.clear();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(attack[i][j] > 0) attack[i][j] ++;
                if(map[i][j] > 0){
                    turretList.add(new Turret(j, i, map[i][j], attack[i][j]));
                }
            }
        }
        turretList.sort(null);
    }
}