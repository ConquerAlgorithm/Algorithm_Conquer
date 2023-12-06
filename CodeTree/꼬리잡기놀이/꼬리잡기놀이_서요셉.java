package CodeTree.삼성스터디.꼬리잡기놀이;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class 꼬리잡기놀이 {

    static final int MAX_N = 20;
    static final int MAX_M = 5;

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int N, M, K, ans;
    static int[][] board = new int[MAX_N + 1][MAX_N + 1];

    static List<Point>[] road = new ArrayList[MAX_M + 1];

    static int[] tail = new int[MAX_M + 1];
    static boolean[][] visited = new boolean[MAX_N + 1][MAX_N + 1];
    static int[][] teamIdxBoard = new int[MAX_N + 1][MAX_N + 1];

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i <= M; i++) {
            road[i] = new ArrayList<>();
        }
        int teamIdx = 1;

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (board[i][j] == 1) road[teamIdx++].add(new Point(i, j));
            }
        }

        for (int i = 1; i <= M; i++) {
            DFS(road[i].get(0).x, road[i].get(0).y, i);
        }
    }

    static void DFS(int x, int y, int idx) {
        visited[x][y] = true;
        teamIdxBoard[x][y] = idx;

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (isNotBoundary(nx, ny)) continue;
            if (board[nx][ny] == 0) continue;
            if (visited[nx][ny]) continue;

            // 초기에는 2가 있는 방향으로만 탐색하도록 조정
            if (road[idx].size() == 1 && board[nx][ny] != 2) continue;

            road[idx].add(new Point(nx, ny));
            if (board[nx][ny] == 3) tail[idx] = road[idx].size();
            DFS(nx, ny, idx);
        }
    }

    public static void main(String[] args) throws Exception {
        input();

        for (int turn = 1; turn <= K; turn++) {
            moveAll();

            int gotBallIdx = throwBall(turn);

            reverse(gotBallIdx);
        }

        System.out.println(ans);
    }

    static void moveAll() {
        for (int i = 1; i <= M; i++) {
            // 한칸씩 뒤로 이동
            Point tmp = road[i].get(road[i].size() - 1);

            for (int j = road[i].size() - 1; j >= 1; j--) {
                road[i].set(j, road[i].get(j - 1));
            }

            road[i].set(0, tmp);
        }

        for (int i = 1; i <= M; i++) {
            for (int j = 0; j < road[i].size(); j++) {
                Point point = road[i].get(j);

                if (j == 0) {
                    board[point.x][point.y] = 1;
                }
                else if (j < tail[i] - 1) {
                    board[point.x][point.y] = 2;
                }
                else if (j == tail[i] - 1) {
                    board[point.x][point.y] = 3;
                }
                else {
                    board[point.x][point.y] = 4;
                }
            }
        }
    }

    static int throwBall(int turn) {
        int t = (turn - 1) % (4 * N) + 1;

        if (t <= N) {
            for (int i = 1; i <= N; i++) {
                if (1 <= board[t][i] && board[t][i] <= 3) {
                    getScore(t, i);
                    return teamIdxBoard[t][i];
                }
            }

        }
        else if (t <= 2 * N) {
            t -= N;
            for (int i = 1; i <= N; i++) {
                if (1 <= board[N + 1 - i][t] && board[N + 1 - i][t] <= 3) {
                    getScore(N + 1 - i, t);
                    return teamIdxBoard[N + 1 - i][t];
                }
            }

        }
        else if (t <= 3 * N) {
            t -= (2 * N);
            for (int i = 1; i <= N; i++) {
                if (1 <= board[N + 1 - t][N + 1 - i] && board[N + 1 - t][N + 1 - i] <= 3) {
                    getScore(N + 1 - t, N + 1 - i);
                    return teamIdxBoard[N + 1 - t][N + 1 - i];
                }
            }
        }
        else {
            t -= (3 * N);
            for (int i = 1; i <= N; i++) {
                if (1 <= board[i][N + 1 - t] && board[i][N + 1 - t] <= 3) {
                    getScore(i, N + 1 - t);
                    return teamIdxBoard[i][N + 1 -t];
                }
            }

        }

        return 0;
    }

    static void getScore(int x, int y) {
        int idx = teamIdxBoard[x][y];
        int cnt = 0;

        for (int i = 0; i < road[idx].size(); i++) {
            if (road[idx].get(i).x == x && road[idx].get(i).y == y) {
                cnt = i;
            }
        }

        ans += (cnt + 1) * (cnt + 1);
    }

    static void reverse(int gotBallIdx) {
        if (gotBallIdx == 0) return;

        int idx = gotBallIdx;
        List<Point> reverseRoad = new ArrayList<>();

        for (int i = tail[idx] - 1; i >= 0; i--) {
            Point point = road[idx].get(i);
            reverseRoad.add(point);
        }

        for (int i = road[idx].size() - 1; i >= tail[idx]; i--) {
            Point point = road[idx].get(i);
            reverseRoad.add(point);
        }

        for (int i = 0; i < road[idx].size(); i++) {
            road[idx].set(i, reverseRoad.get(i));
        }

        for (int i = 0; i < road[idx].size(); i++) {
            Point point = road[idx].get(i);

            if (i == 0) {
                board[point.x][point.y] = 1;
            } else if (i < tail[idx] - 1) {
                board[point.x][point.y] = 2;
            } else if (i == tail[idx] - 1) {
                board[point.x][point.y] = 3;
            } else {
                board[point.x][point.y] = 4;
            }
        }
    }

    static boolean isNotBoundary(int x, int y) {
        return !(1 <= x && x <= N && 1 <= y && y <= N);
    }
}