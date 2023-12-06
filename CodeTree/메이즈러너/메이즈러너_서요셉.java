package CodeTree.삼성스터디.메이즈러너;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * - 개체
 * 1. 미로
 *  - N * N 크기 격자. 좌상단 (1, 1)
 *      - 빈칸 : 참가자 이동 가능
 *      - 벽 : 참가자 이동 불가, 1 ~ 9 이하 내구도. 내구도는 회전할 때 1감소, 내구도가 0이되면 빈칸이 된다. ( -1 ~ -9 로 변경)
 *      - 출구 : 참가자가 도착하면 즉시 탈출 (-10으로 변경)
 *
 * 2. 참가자 : 모든 참가자는 1초마다 한 칸씩 움직인다.
 *  - 두 위치의 최단거리는 맨하탄 거리로 측정
 *  - 모든 참가자는 동시에 움직임
 *  - 상화좌우 이동, 벽이 없는 곳으로 이동
 *  - 움직이는 칸은 현재 머무르는 칸보다 출구와 가까워야 함
 *  - 이동가능 칸이 2개 이상이면 상하가 우선순위
 *  - 움직일 수 없는 상황이면 움직이지 않는다.
 *  - 한 칸에 2명이 존재할 수 있음
 *
 *  - 진행
 *      1. 참가자 이동
 *      2. 미로 회전
 *          - 한 명 이상의 참가자와 출구를 포함한 가장 작은 정사각형을 잡는다.
 *          - 가장 작은 크기를 갖는 정사각형이 2개 이상이면 좌상단 r 좌표가 작은것이 우선, 그래도 같으면 c 좌표가 작은 것이 우선
 *          - 선택된 정사각형은 시계방향 90도 회전하며 회전된 벽은 내구도 1씩 깎임
 *      3. 위 과정을 K 초동안 반복. 만약 K 초 전에 모든 참가자가 탈출에 성공하면 게임이 끝난다.
 *
 * - 정답 : 모든 참가자들의 이동 거리 합, 출구 좌표를 출력
 */
public class 메이즈러너 {

    static int N, M, K, moveCnt;
    static int[][] maze;

    static final int[] dx = {-1, 1, 0, 0};
    static final int[] dy = {0, 0, -1, 1};

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        maze = new int[N + 1][N + 1];

        // 미로 입력
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int num = Integer.parseInt(st.nextToken());
                maze[i][j] = -num;
            }
        }

        // 사람 입력
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            maze[x][y]++;
        }

        // 출구 입력
        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        maze[x][y] = -10;

    }
    public static void main(String[] args) throws Exception {
        input();

        for (int i = 1; i <= K; i++) {
            movePerson();

            if (isFinished()) break;

            rotate();
            printBoard(maze);
        }

        int[] exit = findExit();

        System.out.println(moveCnt);
        System.out.println(exit[0] + " " + exit[1]);
    }

    static void movePerson() {
        int[][] tmpMaze = new int[N + 1][N + 1];
        int[] exit = findExit();
        int ex = exit[0];
        int ey = exit[1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                // 벽이나 출구면 그대로 복사
                if (maze[i][j] < 0) {
                    tmpMaze[i][j] = maze[i][j];
                    continue;
                }

                // 빈 공간이면 continue
                if (maze[i][j] == 0) continue;

                // 현재 사람에서 출구까지 거리
                int curDist = Math.abs(i - ex) + Math.abs(j - ey);
                int minDist = curDist;
                int minX = 0;
                int minY = 0;

                for (int d = 0; d < 4; d++) {
                    int nx = i + dx[d];
                    int ny = j + dy[d];

                    if (isNotBoundary(nx, ny)) continue;
                    if (-9 <= maze[nx][ny] && maze[nx][ny] <= -1) continue;

                    int dist = Math.abs(nx - ex) + Math.abs(ny - ey);
                    if (minDist > dist) {
                        minDist = dist;
                        minX = nx;
                        minY = ny;
                    }
                }

                // 이동 불가라면
                if (minDist == curDist) {
                    tmpMaze[i][j] += maze[i][j];     // 그대로 유지
                    continue;
                }

                moveCnt += maze[i][j];

                // 출구라면
                if (maze[minX][minY] == -10) continue;

                tmpMaze[minX][minY] += maze[i][j];
            }
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                maze[i][j] = tmpMaze[i][j];
            }
        }
    }

    static boolean isNotBoundary(int x, int y) {
        return !(1 <= x && x <= N && 1 <= y && y <= N);
    }

    static void rotate() {
        int minDist = Integer.MAX_VALUE;
        int[] exit = findExit();
        int ex = exit[0];
        int ey = exit[1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (maze[i][j] <= 0) continue;

                int dist = Math.max(Math.abs(i - ex), Math.abs(j - ey));
                minDist = Math.min(minDist, dist);
            }
        }

        int minX = 0;
        int minY = 0;
        for (int i = 1; i <= N - minDist; i++) {
            for (int j = 1; j <= N - minDist; j++) {
                boolean flagExit = false;
                boolean flagPerson = false;

                for (int k = i; k <= i + minDist; k++) {
                    for (int l = j; l <= j + minDist; l++) {
                        if (maze[k][l] == -10) flagExit = true;
                        if (maze[k][l] > 0) flagPerson = true;
                    }
                }

                if (flagExit && flagPerson) {
                    minX = i;
                    minY = j;
                    break;
                }
            }

            if (minX != 0) break;
        }

        System.out.println("minX = " + minX);
        System.out.println("minY = " + minY);
        System.out.println("minDist = " + minDist);
        printBoard(maze);
        rotateMaze(minX, minY, minDist);
    }

    static void rotateMaze(int x, int y, int d) {
        int[][] a = new int[12][12];
        int[][] b = new int[12][12];

        for (int i = x; i <= x + d; i++) {
            for (int j = y; j <= y + d; j++) {
                a[i - x + 1][j - y + 1] = maze[i][j];
            }
        }

        int n = d + 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (-9 <= a[i][j] && a[i][j] <= -1) a[i][j]++;
                b[j][n + 1 - i] = a[i][j];
            }
        }

        for (int i = x; i <= x + d; i++) {
            for (int j = y; j <= y + d; j++) {
                maze[i][j] = b[i - x + 1][j - y + 1];
            }
        }
    }

    static boolean isFinished() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (maze[i][j] > 0) return false;
            }
        }

        return true;
    }

    static int[] findExit() {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (maze[i][j] == -10) return new int[]{i, j};
            }
        }

        return null;
    }

    static void printBoard(int[][] b) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.print(b[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}