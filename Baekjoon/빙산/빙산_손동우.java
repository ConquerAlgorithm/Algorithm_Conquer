import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final int[] dy = {-1, 0, 1, 0};
    private static final int[] dx = {0, -1, 0, 1};
    private static int N, M;
    private static int[][] grid;

    public static void main(String[] args) throws Exception {
        int answer = 0;
        init();
        while (true) {
            answer++;
            meltDown();
            if (isMoreThanOneGlacier()) {
                break;
            } else if (isAllZero()) {
                answer = 0;
                break;
            }
        }
        System.out.println(answer);
    }

    private static void meltDown() {
        int[][] meltCount = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] > 0) {
                    int count = 0;
                    for (int d = 0; d < 4; d++) {
                        int ny = i + dy[d];
                        int nx = j + dx[d];
                        if (isInGrid(ny, nx) && grid[ny][nx] == 0) {
                            count++;
                        }
                    }
                    meltCount[i][j] += count;
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                grid[i][j] = Math.max(0, grid[i][j] - meltCount[i][j]);
            }
        }
    }

    private static boolean isMoreThanOneGlacier() {
        int count = 0;
        boolean[][] visited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visited[i][j] && grid[i][j] > 0) {
                    count++;
                    if (count == 2) {
                        break;
                    }
                    bfs(i, j, visited);
                }
            }
            if (count == 2) {
                break;
            }
        }
        return count == 2;
    }

    private static boolean isAllZero() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void bfs(int y, int x, boolean[][] visited) {
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.offer(new Node(y, x));
        visited[y][x] = true;
        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();
            for (int d = 0; d < 4; d++) {
                int ny = node.y + dy[d];
                int nx = node.x + dx[d];
                if (isInGrid(ny, nx) && !visited[ny][nx] && grid[ny][nx] > 0) {
                    nodeQueue.offer(new Node(ny, nx));
                    visited[ny][nx] = true;
                }
            }
        }
    }

    private static boolean isInGrid(int y, int x) {
        return 0 <= y && y < N && 0 <= x && x < M;
    }

    private static void init() throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        grid = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static class Node {
        int y;
        int x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}