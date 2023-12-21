import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static final int NOT_CLEANED = 0;
    private static final int WALL = 1;
    private static final int CLEANED = 2;
    private static final int[] dy = {-1, 0, 1, 0};

    private static final int[] dx = {0, 1, 0, -1};
    private static int N, M;
    private static Robot robot;
    private static int[][] map;
    private static int answer;

    public static void main(String[] args) throws Exception {
        init();
        clean();
        System.out.println(answer);
    }

    private static void clean() {
        if (map[robot.y][robot.x] == NOT_CLEANED) {
            map[robot.y][robot.x] = CLEANED;
            answer++;
        }
        boolean flag = false;
        for (int d = 0; d < 4; d++) {
            changeDirection();
            int ny = robot.y + dy[robot.direction];
            int nx = robot.x + dx[robot.direction];
            if (isInMap(ny, nx) && map[ny][nx] == NOT_CLEANED) {
                robot.y = ny;
                robot.x = nx;
                flag = true;
                break;
            }
        }
        if (!flag) {
            int reverseDirection = (robot.direction + 2) % 4;
            int ny = robot.y + dy[reverseDirection];
            int nx = robot.x + dx[reverseDirection];
            if (isInMap(ny, nx) && map[ny][nx] != WALL) {
                robot.y = ny;
                robot.x = nx;
                clean();
            }
        } else {
            clean();
        }
    }

    private static void changeDirection() {
        robot.direction--;
        if (robot.direction < 0) {
            robot.direction = 3;
        }
    }

    private static boolean isInMap(int y, int x) {
        return 0 <= y && y < N && 0 <= x && x < M;
    }

    private static void init() throws Exception {
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int direction = Integer.parseInt(st.nextToken());
        robot = new Robot(r, c, direction);
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
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

    private static class Robot extends Node {
        int direction;

        public Robot(int y, int x, int direction) {
            super(y, x);
            this.direction = direction;
        }
    }

}