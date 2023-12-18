import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final int RIPE = 1;
    private static final int UNRIPE = 0;
    private static final int[] dy = {-1, 1, 0, 0, 0, 0};
    private static final int[] dx = {0, 0, -1, 1, 0, 0};
    private static final int[] dz = {0, 0, 0, 0, -1, 1};
    private static int N, M, H;
    private static int[][][] grid;

    public static void main(String[] args) throws Exception {
        init();
        int answer = ripenTomatoes();
        if (isAllRipe()) {
            System.out.println(answer);
        } else {
            System.out.println(-1);
        }
    }

    private static int ripenTomatoes() {
        Queue<Node> nodeQueue = findTomatoes();
        int count = 0;
        int size = nodeQueue.size();
        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();
            for (int d = 0; d < 6; d++) {
                int ny = node.y + dy[d];
                int nx = node.x + dx[d];
                int nz = node.z + dz[d];
                if (isInGrid(ny, nx, nz) && grid[ny][nx][nz] == 0) {
                    grid[ny][nx][nz] = 1;
                    nodeQueue.offer(new Node(ny, nx, nz));
                }
            }

            if (--size == 0) {
                count++;
                size = nodeQueue.size();
            }
        }
        return count - 1;
    }

    private static boolean isAllRipe() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < H; k++) {
                    if (grid[i][j][k] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isInGrid(int y, int x, int z) {
        return 0 <= y && y < N && 0 <= x && x < M && 0 <= z && z < H;
    }

    private static Queue<Node> findTomatoes() {
        Queue<Node> nodeQueue = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = 0; k < H; k++) {
                    if (grid[i][j][k] == RIPE) {
                        nodeQueue.offer(new Node(i, j, k));
                    }
                }
            }
        }
        return nodeQueue;
    }

    private static void init() throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        grid = new int[N][M][H];
        for (int k = 0; k < H; k++) {
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    grid[i][j][k] = Integer.parseInt(st.nextToken());
                }
            }
        }
    }

    private static class Node {
        int y;
        int x;
        int z;

        public Node(int y, int x, int z) {
            this.y = y;
            this.x = x;
            this.z = z;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "y=" + y +
                    ", x=" + x +
                    ", z=" + z +
                    '}';
        }
    }
}