import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final int[] dy = {-1, 0, 1, 0};
    private static final int[] dx = {0, -1, 0, 1};
    private static int N;
    private static int MAX_HEIGHT;
    private static int[][] grid;

    public static void main(String[] args) throws Exception {
        init();
        int answer = 0;
        for (int i = 0; i < MAX_HEIGHT; i++) {
            int count = countSafeZones(i);
            answer = Math.max(answer, count);
        }
        System.out.println(answer);
    }

    private static int countSafeZones(int height) {
        boolean[][] visited = new boolean[N][N];
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j] && grid[i][j] > height) {
                    bfs(new Node(i, j), height, visited);
                    count++;
                }
            }
        }
        return count;
    }

    private static void bfs(Node startNode, int height, boolean[][] visited) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(startNode);
        visited[startNode.y][startNode.x] = true;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (int d = 0; d < 4; d++) {
                int ny = node.y + dy[d];
                int nx = node.x + dx[d];
                if (isInGrid(ny, nx) && !visited[ny][nx] && grid[ny][nx] > height) {
                    queue.offer(new Node(ny, nx));
                    visited[ny][nx] = true;
                }
            }
        }
    }

    private static boolean isInGrid(int y, int x) {
        return 0 <= y && y < N && 0 <= x && x < N;
    }

    private static void init() throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        grid = new int[N][N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
                MAX_HEIGHT = Math.max(MAX_HEIGHT, grid[i][j]);
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