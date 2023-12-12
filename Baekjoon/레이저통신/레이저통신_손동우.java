import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    private static final int[] dy = {-1, 0, 1, 0};
    private static final int[] dx = {0, -1, 0, 1};
    private static int W, H;
    private static char[][] map;
    private static Node start, end;
    private static int[][] mirrors;
    private static int[][] directions;

    public static void main(String[] args) throws IOException {
        init();
        bfs();
        printDirections();
        System.out.println(mirrors[end.y][end.x]);
    }

    private static void bfs() {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.mirrorCount));
        mirrors[start.y][start.x] = -1;
        directions[start.y][start.x] = -1;
        pq.offer(new Node(start.y, start.x, -1, -1));
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            for (int d = 0; d < 4; d++) {
                int ny = node.y + dy[d];
                int nx = node.x + dx[d];
                if (isInMap(ny, nx) && mirrors[ny][nx] >= node.mirrorCount && map[ny][nx] != '*') {
                    int nextMirrorCount = node.mirrorCount + (node.direction == d ? 0 : 1);
                    if (mirrors[ny][nx] >= nextMirrorCount) {
                        if (mirrors[ny][nx] > nextMirrorCount || directions[ny][nx] != d) {
                            mirrors[ny][nx] = nextMirrorCount;
                            pq.offer(new Node(ny, nx, d, nextMirrorCount));
                            directions[ny][nx] = d;
                        }
                    }
                }
            }
        }
    }

    private static boolean isInMap(int y, int x) {
        return 0 <= y && y < H && 0 <= x && x < W;
    }

    private static void init() throws IOException {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        map = new char[H][W];
        mirrors = new int[H][W];
        directions = new int[H][W];
        for (int i = 0; i < H; i++) {
            Arrays.fill(mirrors[i], Integer.MAX_VALUE);
            Arrays.fill(directions[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < H; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < W; j++) {
                if (map[i][j] == 'C') {
                    if (start == null) {
                        start = new Node(i, j);
                    } else {
                        end = new Node(i, j);
                    }
                }
            }
        }
    }

    private static void printDirections() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (directions[i][j] == Integer.MAX_VALUE) {
                    System.out.print("-\t");
                } else {
                    System.out.print(directions[i][j] + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (mirrors[i][j] == Integer.MAX_VALUE) {
                    System.out.print("-\t");
                } else {
                    System.out.print(mirrors[i][j] + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static class Node {
        int y;
        int x;
        int direction;
        int mirrorCount;

        public Node(int y, int x, int direction, int mirrorCount) {
            this.y = y;
            this.x = x;
            this.direction = direction;
            this.mirrorCount = mirrorCount;
        }

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}