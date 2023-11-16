import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static int n, answer = 0;
    static int[][] map;
    static int[][] check;
    static boolean[][] visited;
    static Shark shark;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        map = new int[n][n];
        StringTokenizer st;
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 9) {
                    shark = new Shark(new Pair(j, i));
                }
            }
        }
        map[shark.pair.y][shark.pair.x] = 0;
        while(true) {
            bfs();
            if(!moveShark())
                break;

        }
        System.out.println(answer);
    }

    private static boolean moveShark() {
        int x = -1, y = -1;
        int min = Integer.MAX_VALUE;
        map[shark.pair.y][shark.pair.x] = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(!visited[i][j] || min <= check[i][j] || map[i][j] == 0 || shark.size <= map[i][j]) continue;
                min = check[i][j];
                x = j;
                y = i;
            }
        }
        if(min != Integer.MAX_VALUE) {
            shark.pair.x = x;
            shark.pair.y = y;
            map[y][x] = 0;
            shark.eat++;
            if(shark.size == shark.eat) {
                shark.eat = 0;
                shark.size++;
            }
            answer += check[y][x];
            return true;
        } else
            return false;

    }

    private static void bfs() {
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(shark.pair.x, shark.pair.y));
        check = new int[n][n];
        visited = new boolean[n][n];
        check[shark.pair.y][shark.pair.x] = 0;
        visited[shark.pair.y][shark.pair.x] = true;

        int[] dx = {0, -1, 0, 1};
        int[] dy = {1, 0, -1, 0};
        while(!queue.isEmpty()) {
            Pair pair = queue.poll();
            for(int i = 0; i < dx.length; i++) {
                int nx = pair.x + dx[i];
                int ny = pair.y + dy[i];
                if(outRange(nx, ny) || shark.size < map[ny][nx] || visited[ny][nx]) continue;
                visited[ny][nx] = true;
                check[ny][nx] = check[pair.y][pair.x] + 1;
                queue.add(new Pair(nx, ny));
            }
        }
    }

    public static boolean outRange(int x, int y){
        return x >= n || y >= n || x < 0 || y < 0;
    }
}
class Shark {
    Pair pair;
    int size;
    int eat;
    public Shark(Pair pair) {
        size = 2;
        eat = 0;
        this.pair = pair;
    }
}

class Pair {
    int x;
    int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}