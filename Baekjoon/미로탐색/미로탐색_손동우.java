import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    private static final int[] dy = {-1, 0, 1, 0};
    private static final int[] dx = {0, -1, 0, 1};
    private static int N, M;
    private static int[][] map;

    public static void main(String[] args) throws IOException {
        init();
        int answer = bfs();
        System.out.println(answer);
    }

    private static void init() throws IOException {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j) - '0';
            }
        }
    }

    private static int bfs() {
        Queue<Node> nodeQueue = new LinkedList<>();
        boolean[][] visited = new boolean[N][M];
        nodeQueue.offer(new Node(0, 0));
        visited[0][0] = true;
        int count = 1;
        int size = 1;
        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();
            if (node.y == N - 1 && node.x == M - 1) {
                break;
            }
            for (int d = 0; d < 4; d++) {
                int ny = node.y + dy[d];
                int nx = node.x + dx[d];
                if (isInMap(ny, nx) && !visited[ny][nx] && map[ny][nx] == 1) {
                    visited[ny][nx] = true;
                    nodeQueue.offer(new Node(ny, nx));
                }
            }
            if (--size == 0) {
                count++;
                size = nodeQueue.size();
            }
        }
        return count;
    }

    private static boolean isInMap(int y, int x) {
        return 0 <= y && y < N && 0 <= x && x < M;
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