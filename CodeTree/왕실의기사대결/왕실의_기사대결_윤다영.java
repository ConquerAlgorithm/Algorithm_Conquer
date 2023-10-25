import java.util.*;

public class Main {
    private static int L, N, Q;
    private static int[][] map;
    private static int[][] robotMap;
    private static Map<Integer, Robot> robotList;
    private static int[] dx = {0, 1, 0, -1};
    private static int[] dy = {-1, 0, 1, 0};

    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
        Scanner sc = new Scanner(System.in);
        L = sc.nextInt();
        N = sc.nextInt();
        Q = sc.nextInt();

        map = new int[L][L];
        robotMap = new int[L][L];

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        robotList = new HashMap<>();
        for (int i = 0; i < N; i++) {
            robotList.put(i + 1, new Robot(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()));
            Robot robot = robotList.get(i + 1);
            markingRobotMap(robot, i + 1);
        }

        for (int i = 0; i < Q; i++) {
            move(sc.nextInt(), sc.nextInt());
        }
        int answer = 0;
        for (Robot robot : robotList.values()) {
            answer += robot.d;
        }
        System.out.println(answer);
    }

    private static void markingRobotMap(Robot robot, int idx) {
        for (int j = robot.r; j <= robot.r + robot.h - 1; j++) {
            for (int k = robot.c; k <= robot.c + robot.w - 1; k++) {
                robotMap[j][k] = idx;
            }
        }
    }

    private static boolean outRange(int x, int y) {
        return x >= L || y >= L || x < 0 || y < 0;
    }
    private static void move(int idx, int dir) {
        Robot moveStartRobot = robotList.get(idx);
        if(moveStartRobot == null) return;

        Queue<Robot> q = new LinkedList();
        Map<Integer, Robot> changeRobotList = new HashMap<>();
        q.add(moveStartRobot);
        while(!q.isEmpty()){
            Robot robot = q.poll();
            int damage = 0;
            for(int i = robot.r; i <= robot.r + robot.h - 1; i++) {
                for(int j = robot.c; j <= robot.c + robot.w - 1; j++) {
                    int nr = i + dy[dir];
                    int nc = j + dx[dir];
                    if(outRange(nr, nc) || map[nr][nc] == 2) return;
                    if(map[nr][nc] == 1) damage++;
                    if(robotMap[nr][nc] != robotMap[robot.r][robot.c] && robotMap[nr][nc] != 0) {
                        q.add(robotList.get(robotMap[nr][nc]));
                    }
                }
            }
            Robot changeRobot = new Robot(robot);
            if(idx != robotMap[robot.r][robot.c]) {
                changeRobot.d += damage;
                changeRobot.k -= damage;
            }
            changeRobot.r += dy[dir];
            changeRobot.c += dx[dir];
            changeRobotList.put(robotMap[robot.r][robot.c], changeRobot);
        }

        robotMap = new int[L][L];
        for (int index : changeRobotList.keySet()) {
            Robot robot = changeRobotList.get(index);
            if (robot.k <= 0) {
                robotList.remove(index);
                continue;
            }
            robotList.put(index, changeRobotList.get(index));
        }
        for (int index : robotList.keySet()) {
            Robot robot = robotList.get(index);
            markingRobotMap(robot, index);
        }
    }
}

class Robot {
    // y 좌표
    int r;
    //x 좌표
    int c;
    // 높이
    int h;
    // 넓이
    int w;
    // 내구도
    int k;
    // 댐지
    int d;

    public Robot(int r, int c, int h, int w, int k) {
        this.r = r - 1;
        this.c = c - 1;
        this.h = h;
        this.w = w;
        this.k = k;
        this.d = 0;
    }

    public Robot(Robot robot) {
        this.r = robot.r;
        this.c = robot.c;
        this.h = robot.h;
        this.w = robot.w;
        this.k = robot.k;
        this.d = robot.d;
    }
}