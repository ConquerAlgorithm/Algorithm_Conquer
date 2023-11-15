package BackJoon.골드.G3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * - 개체
 *  1. 격자
 *      - N * N 크기의 격자
 *      - 한 칸에는 물고기 최대 1마리 존재
 *
 *  2. 물고기
 *      - M 마리 존재, 크기를 가짐
 *
 *  3. 상어
 *      - 1마리 존재, 크기를 가짐. 상어는 최초 2이다.
 *      - 인접 4방으로 한 칸씩 이동.
 *      - 자기 자신보다 크기가 큰 물고기가 있다면 지날 수 없고, 다른 모든 칸은 지나갈 수 있다.
 *      - 자신보다 크기가 작은 물고기만 먹을 수 있다.
 *      - 자신과 크기가 같은 물고기인 경우 먹을 순 없지만 지날 수는 있다.
 *      - 상어 이동 결정 조건
 *          1. 더 이상 먹을 수 있는 물고기가 공간에 없다면 엄마상어에게 도움을 요청.
 *          2. 먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 간다.
 *          3. 먹을 수 있는 물고기가 1마리 이상이면 거리가 가장 가까운 물고기를 먹으러간다.
 *              3-1. 거리는 아기상어 기준 물고기 칸까지 자나야하는 칸의 개수 최솟값이다.
 *              3-2. 거리가 가까운 물고기가 많으면 행값이 가장 작은 물고기, 그것도 여러마리면 열값이 가장 작은 물고기를 먹는다.
 *
 * - 진행
 *  1. 아기 성어 이동은 1초 걸림
 *  2. 물고기를 먹는데 걸리는 시간은 없다.
 *  3. 아기상어가 물고기가 있는 칸으로 이동하면 이동과 동시에 먹는다.
 *  4. 물고기를 먹으면 해당 칸은 빈칸이 된다.
 *  5. 아기상어는 자신의 크기와 같은 수의 물고기를 먹으면 크기가 증가한다. 예를 들어 크기가 2라면 물고기 2마리를 먹으면 크기가 3이된다.
 *  6. 공간의 상태가 주어지면 아기상어가 몇초 동안 요청하지 않고 물고기를 잡아먹을 수 있는 지 구하라.
 */
public class BJ_16236_아기상어 {

    static class Point implements Comparable<Point> {
        int x, y, dist;

        public Point(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public int compareTo(Point p) {
            if (this.dist != p.dist) return this.dist - p.dist;
            if (this.x != p.x) return this.x - p.x;
            return this.y - p.y;
        }
    }

    static int N, time, eatCnt, size;
    static int[][] board;
    static Point shark;
    static List<Point> fishList;

    static final int[] dx = {-1, 0, 0, 1};
    static final int[] dy = {0, -1, 1, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        board = new int[N][N];
        size = 2;
        eatCnt = 0;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());

                if (board[i][j] == 9) {
                    shark = new Point(i, j, 0);
                    board[i][j] = 0;
                }
            }
        }

        start();

    }

    static void start() {
        while (true) {
            fishList = new ArrayList<>();

            Queue<Point> q = new LinkedList<>();
            boolean[][] visited = new boolean[N][N];

            q.add(new Point(shark.x, shark.y, 0));
            visited[shark.x][shark.y] = true;

            while (!q.isEmpty()) {
                Point cur = q.poll();

                int cx = cur.x;
                int cy = cur.y;

                for (int d = 0; d < 4; d++) {
                    int nx = cx + dx[d];
                    int ny = cy + dy[d];

                    if (isNotBoundary(nx, ny)) continue;
                    if (visited[nx][ny]) continue;

                    Point newShark = new Point(nx, ny, cur.dist + 1);
                    // 상어가 먹을 수 있는 물고기라면
                    if (1 <= board[nx][ny] && board[nx][ny] < size) {
                        q.add(newShark);
                        fishList.add(newShark);
                        visited[nx][ny] = true;
                    }
                    // 빈칸 이거나 상어와 크기가 같은 물고기라면
                    else if (board[nx][ny] == size || board[nx][ny] == 0) {
                        q.add(newShark);
                        visited[nx][ny] = true;
                    }
                }
            }

            if (fishList.isEmpty()) {
                System.out.println(time);
                return;
            }

            Collections.sort(fishList);
            Point targetFish = fishList.get(0);

            time += targetFish.dist;
            eatCnt++;
            board[targetFish.x][targetFish.y] = 0;

            if (eatCnt == size) {
                size++;
                eatCnt = 0;
            }

            shark.x = targetFish.x;
            shark.y = targetFish.y;
        }

    }

    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < N);
    }
}
