package CodeTree.삼성스터디.코드트리빵;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 풀이 시간 : 0h 54m
 *
 * - 개체
 *  1. 격자
 *  2. 사람
 *      - M명
 *
 * - 진행
 *  1. 사람 이동
 *      - 1분에 1번, 2분에 2번... M 분에 M번 출발
 *      - 가고 싶은 편의점 방향으로 1칸씩 이동
 *      - 최단거리로 움직이고 최단 거리로 움직이는 방법이 여러가지면 상 좌 우 하 순으로 움직임
 *      - 최단거리는 인접한 4방 칸 중 이동가능한 칸으로만 이동하여 도착칸 까지 칸의 수가 최소가 되는 거리임.
 *      - 편의점에 도착하면 해당 편의점에서 멈추고, 이때부터 다른 사람들은 해당 편의점 칸을 지날 수 없다.
 *      - 격자의 모든 사람들이 이동한 뒤에 이동불가 처리됨에 유의.
 *
 *  2. 베이스캠프 도착
 *      - t 분이고 t <= m 을 만족하면 t 번 사람은 가고싶은 편의점과 가장 가까이에 있는 베캠에 들어간다.
 *      - 여기서 가장 가까운 최단거리는 위의 조건과 동일하다.
 *      - 가장 가까운 베캠이 여러가지라면 행이 작은것, 행이 같다면 열이 작은것.
 *      - 베캠으로 이동하는 것에는 시간이 전혀 소요되지 않는다.
 *      - 이때부터 해당 베캠은 다른 사람들이 지날 수 없다.
 *      - t 번 사람이 편의점을 향해 움직이기 시작했더라도 해당 베캠은 절대 지나갈 수 없다.
 *      - 모든 사람들이 이동한 뒤에 이동불가 처리됨에 유의.
 *
 * - 정답 : 모든 사람들이 편의점에 도착하는 시간
 */
public class 코드트리빵 {

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Person extends Point {
        Point destination;

        public Person(Point destination) {
            super(0, 0);
            this.destination = destination;
        }
    }

    static int N, M;
    static int[][] board;
    static int[][] dist;
    static Person[] people = new Person[31];

    // 상 좌 우 하
    static int[] dx = {-1, 0, 0, 1};
    static int[] dy = {0, -1, 1, 0};

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        board = new int[N + 1][N + 1];
        dist = new int[N + 1][N + 1];

        for (int i = 1; i < people.length; i++) {
            people[i] = new Person(null);
        }

        // 격자 상태 입력 (빈칸과 베이스캠프가 주어진다.)
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // M 개의 편의점이 주어진다. 각각의 번호의 사람들의 목적지이다.
        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            // i 번 사람의 목적 편의점
            Point destination = new Point(x, y);
            people[i].destination = destination;
        }
    }

    public static void main(String[] args) throws Exception {
        input();

        int time = 0;
        while (!allArrived()) {
            time++;

            // 모든 사람 한 칸씩 이동
            for (int i = 1; i < time && i <= M; i++) {
                move(people[i]);
            }

            // 목적지 도착한 사람 도착 처리
            for (int i = 1; i < time && i <= M; i++) {
                setArrived(people[i]);
            }

            // 처음 출발하는 사람들 베이스캠프에 위치
            if (time <= M) {
                selectBasecamp(people[time]);
            }
        }

        System.out.println(time);
    }

    static void move(Person p) {
        // 이미 도착하면 return
        if (isArrived(p)) return;

        // 최단 경로 업데이트
        findMinDist(p.destination);

        int minDist = Integer.MAX_VALUE;
        int minDir = -1;

        for (int d = 0; d < 4; d++) {
            int nx = p.x + dx[d];
            int ny = p.y + dy[d];

            if (isNotBoundary(nx, ny)) continue;
            if (board[nx][ny] == -1) continue;

            if (minDist > dist[nx][ny]) {
                minDist = dist[nx][ny];
                minDir = d;
            }
        }

        p.x += dx[minDir];
        p.y += dy[minDir];
    }

    static void findMinDist(Point destination) {
        int sx = destination.x;
        int sy = destination.y;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        Queue<Point> q = new LinkedList<>();
        q.add(new Point(sx, sy));

        dist[sx][sy] = 0;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            int cx = cur.x;
            int cy = cur.y;

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (isNotBoundary(nx, ny)) continue;
                if (dist[nx][ny] != Integer.MAX_VALUE) continue;
                if (board[nx][ny] == -1) continue;

                dist[nx][ny] = dist[cx][cy] + 1;
                q.add(new Point(nx, ny));
            }
        }
    }

    static boolean isNotBoundary(int x, int y) {
        return !(1 <= x && x <= N && 1 <= y && y <= N);
    }

    static void setArrived(Person p) {
        if (isArrived(p)) board[p.x][p.y] = -1;
    }

    static void selectBasecamp(Person p) {
        findMinDist(p.destination);

        int minDist = Integer.MAX_VALUE;
        int minX = 0;
        int minY = 0;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (board[i][j] != 1) continue;
                if (minDist > dist[i][j]) {
                    minDist = dist[i][j];
                    minX = i;
                    minY = j;
                }
            }
        }

        p.x = minX;
        p.y = minY;

        board[p.x][p.y] = -1;
    }

    static boolean isArrived(Person p) {
        return p.x == p.destination.x && p.y == p.destination.y;
    }

    static boolean allArrived() {
        for (int i = 1; i <= M; i++) {
            if (!isArrived(people[i])) return false;
        }
        return true;
    }
}
