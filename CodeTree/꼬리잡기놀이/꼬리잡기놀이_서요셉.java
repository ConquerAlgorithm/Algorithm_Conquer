package CodeTree.삼성스터디.꼬리잡기놀이;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
7 3 5
3 2 1 0 0 0 0
4 0 4 0 2 1 4
4 4 4 0 2 0 4
0 0 0 0 3 4 4
4 1 3 4 0 0 0
4 0 0 4 0 0 0
4 4 4 4 0 0 0

7 3 5
3 2 1 0 0 0 0
4 0 4 0 2 1 4
4 4 4 0 2 0 4
0 0 0 0 3 4 4
2 1 3 2 0 0 0
2 0 0 2 0 0 0
2 2 2 2 0 0 0
 */
public class 꼬리잡기놀이 {

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static class Point {
        int x, y, seq, teamIdx;
        Point prev, next;

        public Point(int x, int y, int seq, int teamIdx) {
            this.x = x;
            this.y = y;
            this.seq = seq;
            this.teamIdx = teamIdx;
        }

        @Override
        public String toString() {
            return "["+seq + "] : {좌표 = (" +  x + ", " + y + ") teamIdx=" + teamIdx + "}";
        }
    }

    static class Team {
        List<Point> pointList;
        int idx, score;
        boolean headFirst, isFull;
        Point head, tail;

        public Team(int idx) {
            this.idx = idx;
            this.headFirst = true;
            this.pointList = new ArrayList<>();
            this.isFull = false;
        }

        public void printStatus() {
            System.out.println(":: [" + idx + "] 번 팀 Info ::");
            System.out.println("pointList size() : " + pointList.size());
            System.out.println("This team isFull : " + isFull);
            for (Point p: pointList) {
                System.out.println(p.toString());
//                if (p.prev != null) System.out.println("이전좌표 : seq= ["+ p.prev.seq + "], (" + p.prev.x + ", " + p.prev.y + ")");
//                if (p.next != null) System.out.println("다음좌표 : seq= ["+ p.next.seq + "], (" + p.next.x + ", " + p.next.y + ")");
//                System.out.println();
            }
            System.out.println("head 좌표: (" + head.x + ", " + head.y + ")");
            System.out.println("tail 좌표: (" + tail.x + ", " + tail.y + ")");
            System.out.println();
        }
    }

    static int N, M, K, ans;
    static int[][] board;
    static boolean[][] road;
    static Point[][] pointBoard;

    static Team[] teamsPool;

    static int[][] test;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        board = new int[N][N];
        road = new boolean[N][N];
        pointBoard = new Point[N][N];

        test = new int[N][N];

        // 초기 정보 입력 (길과 머리, 꼬리, 중간 사람들 정보 입력)
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());

                if (board[i][j] != 0) road[i][j] = true;
            }
        }

        // 팀 풀 생성
        teamsPool = new Team[M + 1];
        for (int i = 1; i <= M; i++) {
            teamsPool[i] = new Team(i);
        }

    }

    // 머리와 꼬리를 바꿔야함. 즉 seq 를 바꿔야함
    public static void main(String[] args) throws Exception {
        input();
        initTeam();

        // init test print start
//        System.out.println("<< INIT STATUS >> ");
//        for (int i = 1; i <= M; i++) {
//            Team t = teamsPool[i];
//            t.printStatus();
//        }
//        printBoard();
//        printBoard(road);
//        printBoard(pointBoard);
        // init test print end

        for (int turn = 1; turn <= K; turn++) {
            System.out.println("<< " + turn + " 번째 턴 진행 >>");
            // 팀들 이동
            for (int i = 1; i <= M; i++) {
                System.out.println(i + " 팀 이동");
                moveTeam(i);
            }
            // 볼 던지기
            throwBall(turn);
        }

        System.out.println(ans);
    }

    static void moveTeam(int teamIdx) {
        Team team = teamsPool[teamIdx];

        // 만약 이 팀이 경로에 모두 서있는 팀이라면
        if (team.isFull) {
            // 머리는 다음 좌표가 3인 경우만 이동하면 되고
            if (team.headFirst) {
                System.out.println("** 머리부터 이동 ** ");
                Point head = team.head;

                for (int d = 0; d < 4; d++) {
                    int nx = head.x + dx[d];
                    int ny = head.y + dy[d];

                    if (isNotBoundary(nx, ny)) continue;

                    if (pointBoard[nx][ny] == team.tail) {
                        head.prev = new Point(nx, ny, 0, 0);
                        System.out.println("head's next point : (" + nx + ", " + ny + ")");
                        break;
                    }
                }

                Point tail = team.tail;

                for (Point p = tail; p != head.prev; p = p.prev) {
                    System.out.print("(" + p.x + ", " + p.y + ") -> ");

                    p.x = p.prev.x;
                    p.y = p.prev.y;

                    pointBoard[p.x][p.y] = p;

                    System.out.println("(" + p.x + ", " + p.y + ")");
                }
            }
            // 꼬리는 다음 좌표가 1인 경우만 이동하면 된다.
            else {
                System.out.println("** 꼬리부터 이동 ** ");
                Point tail = team.tail;

                for (int d = 0; d < 4; d++) {
                    int nx = tail.x + dx[d];
                    int ny = tail.y + dy[d];

                    if (isNotBoundary(nx, ny)) continue;

                    if (pointBoard[nx][ny] == team.head) {
                        tail.next = new Point(nx, ny, 0, 0);
                        System.out.println("tail's next point : (" + nx + ", " + ny + ")");
                        break;
                    }
                }

                Point head = team.head;

                for (Point p = head; p != tail.next; p = p.next) {
                    System.out.print("(" + p.x + ", " + p.y + ") -> ");

                    p.x = p.next.x;
                    p.y = p.next.y;

                    pointBoard[p.x][p.y] = p;

                    System.out.println("(" + p.x + ", " + p.y + ")");
                }
            }


            System.out.println();
        }
        // 아니라면
        else {
            // 머리는 다음좌표가 길인 경우만 이동하면 되고
            if (team.headFirst) {
                System.out.println("** 머리부터 이동 ** ");
                Point head = team.head;

                for (int d = 0; d < 4; d++) {
                    int nx = head.x + dx[d];
                    int ny = head.y + dy[d];

                    if (isNotBoundary(nx, ny)) continue;

                    if (road[nx][ny] && pointBoard[nx][ny] == null) {
                        head.prev = new Point(nx, ny, 0, 0);
                        System.out.println("head's next point : (" + nx + ", " + ny + ")");
                        break;
                    }
                }

                Point tail = team.tail;
                pointBoard[tail.x][tail.y] = null;

                for (Point p = tail; p != head.prev; p = p.prev) {
                    System.out.print("(" + p.x + ", " + p.y + ") -> ");

                    p.x = p.prev.x;
                    p.y = p.prev.y;

                    pointBoard[p.x][p.y] = p;

                    System.out.println("(" + p.x + ", " + p.y + ")");
                }
            }
            // 꼬리는 다음좌표가 길인 경우만 이동하면 된다
            else {
                System.out.println("** 꼬리부터 이동 ** ");
                Point tail = team.tail;

                for (int d = 0; d < 4; d++) {
                    int nx = tail.x + dx[d];
                    int ny = tail.y + dy[d];

                    if (isNotBoundary(nx, ny)) continue;

                    if (road[nx][ny] && pointBoard[nx][ny] == null) {
                        tail.next = new Point(nx, ny, 0, 0);
                        System.out.println("tail's next point : (" + nx + ", " + ny + ")");
                        break;
                    }
                }

                Point head = team.head;
                pointBoard[head.x][head.y] = null;

                for (Point p = head; p != tail.next; p = p.next) {
                    System.out.print("(" + p.x + ", " + p.y + ") -> ");

                    p.x = p.next.x;
                    p.y = p.next.y;

                    pointBoard[p.x][p.y] = p;

                    System.out.println("(" + p.x + ", " + p.y + ")");
                }
            }

            System.out.println();
        }

        team.printStatus();
        printBoard(pointBoard);
    }



    static void initTeam() {
        // 팀 구성
        int teamIdx = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1) {
                    findTeam(i, j, teamIdx);
                    getRoadCnt(i, j, teamIdx);
                    teamIdx++;
                }
            }
        }
    }

    static void getRoadCnt(int x, int y, int teamIdx) {
        Queue<Point> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];

        q.add(new Point(x, y, 0, 0));
        visited[x][y] = true;

        int cnt = 1;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            for (int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];

                if (isNotBoundary(nx, ny)) continue;
                if (visited[nx][ny]) continue;

                if (road[nx][ny]) {
                    q.add(new Point(nx, ny, 0, 0));
                    visited[nx][ny] = true;
                    cnt++;
                }
            }
        }

        if (teamsPool[teamIdx].pointList.size() == cnt) teamsPool[teamIdx].isFull = true;
    }

    static void findTeam(int x, int y, int teamIdx) {
        // BFS 에서 2 일때만 BFS 를 타도록 한다.
        // 마찬가지로 2 일때만 팀 리스트에 추가한다.
        // 노드 연결도 2일 때만 진행한다.
        Queue<Point> q = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];

        // 시작점 == head
        Point start = new Point(x, y, 1, teamIdx);

        // pointBoard
        pointBoard[x][y] = start;

        // Team
        Team team = teamsPool[teamIdx];
        team.head = start;
        team.pointList.add(start);

        // BFS
        q.add(start);
        visited[start.x][start.y] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            int cx = cur.x;
            int cy = cur.y;

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (isNotBoundary(nx, ny)) continue;
                if (visited[nx][ny]) continue;

                if (board[nx][ny] == 2) {
                    Point nextPoint = new Point(nx, ny, cur.seq + 1, teamIdx);
                    // 최초 헤드에서 다음 첫 노드 연결
                    if (cur.seq == 1) {
                        team.head.next = nextPoint;
                        nextPoint.prev = team.head;
                        team.pointList.add(nextPoint);

                        pointBoard[nx][ny] = nextPoint;

                        q.add(nextPoint);
                        visited[nextPoint.x][nextPoint.y] = true;
                    }
                    // 2 노드에서 다른 2 노드로 연결
                    else {
                        cur.next = nextPoint;
                        nextPoint.prev = cur;
                        team.pointList.add(nextPoint);

                        pointBoard[nx][ny] = nextPoint;

                        q.add(nextPoint);
                        visited[nextPoint.x][nextPoint.y] = true;
                    }
                }
            }
        }

        // 이 경우는 머리와 꼬리만 있는 경우이다. 머리 다음에 발견되는 3 노드를 꼬리로 등록하고 머리와 꼬리 연결을 진행.
        if (team.pointList.size() == 1) {
            int cx = team.head.x;
            int cy = team.head.y;

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (isNotBoundary(nx, ny)) continue;
                if (board[nx][ny] == 3) {
                    Point nextPoint = new Point(nx, ny, team.head.seq + 1, teamIdx);

                    team.tail = nextPoint;

                    team.head.next = nextPoint;
                    team.tail.prev = team.head;
                    team.pointList.add(nextPoint);

                    pointBoard[team.tail.x][team.tail.y] = team.tail;
                }
            }
        } else {
            // 팀 리스트에서 마지막 노드 다음으로 발견되는 3 노드를 꼬리로 등록하고 연결 진행.
            Point last = team.pointList.get(team.pointList.size() - 1);

            int cx = last.x;
            int cy = last.y;

            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                if (isNotBoundary(nx, ny)) continue;
                if (board[nx][ny] == 3) {
                    Point nextPoint = new Point(nx, ny, last.seq + 1, teamIdx);

                    team.tail = nextPoint;

                    last.next = nextPoint;
                    team.tail.prev = last;
                    team.pointList.add(nextPoint);

                    pointBoard[team.tail.x][team.tail.y] = team.tail;
                }
            }
        }

    }

    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < N);
    }

    static void throwBall(int turn) {
        System.out.println(":::: 공던지기 ::::");

        int t = turn % (4 * N);

        if (1 <= t && t <= N) {
//            System.out.println(":: Case 1 : " + t + " 턴");
            for (int i = 0; i < N; i++) {
                // 해당 지점에 사람이 있다면
                test[(t - 1) % N][i] = 1;
                printBoard(test);
                if (pointBoard[(t - 1) % N][i] != null) {
                    // 해당 좌표에 서있는 (사람의 순서 * turn) 만큼의 점수를 얻는다.
                    // 해당 좌표에 서있는 사람의 팀의 방향을 바꾼다.
                    printBoard(pointBoard);

                    int score = pointBoard[(t - 1) % N][i].seq;
                    Team team = teamsPool[pointBoard[(t - 1) % N][i].teamIdx];
                    team.headFirst = !team.headFirst;
                    swap(team);

                    printBoard(pointBoard);

                    System.out.println(team.idx + " 팀의 "+ score +" 번째 사람이 공을 얻어 점수를 얻고 방향을 바꿉니다.");
                    System.out.println("score : " + (score * score));

                    ans += (score * score);

                    System.out.println("ans : " + ans);
                    System.out.println();

                    return;
                }

            }

        } else if (N + 1 <= t && t <= 2 * N) {
//            System.out.println(":: Case 2 : " + t + " 턴");

            for (int i = N - 1; i >= 0; i--) {
                test[i][(t - 1) % N] = 2;
                printBoard(test);
                if (pointBoard[i][(t - 1) % N] != null) {
                    printBoard(pointBoard);

                    int score = pointBoard[i][(t - 1) % N].seq;
                    Team team = teamsPool[pointBoard[i][(t - 1) % N].teamIdx];
                    team.headFirst = !team.headFirst;
                    swap(team);

                    printBoard(pointBoard);

                    System.out.println(team.idx + " 팀의 "+ score +" 번째 사람이 공을 얻어 점수를 얻고 방향을 바꿉니다.");
                    System.out.println("score : " + (score * score));

                    ans += (score * score);

                    System.out.println("ans : " + ans);
                    System.out.println();
                    return;
                }
//                printBoard(test);
            }

        } else if (2 * N + 1 <= t && t <= 3 * N) {
//            System.out.println(":: Case 3 : " + t + " 턴");

            for (int i = 0; i < N; i++) {
                test[(t - 1) % N][(N - 1) - i] = 3;
                printBoard(test);
                if (pointBoard[(t - 1) % N][(N - 1) - i] != null) {
                    printBoard(pointBoard);

                    int score = pointBoard[(t - 1) % N][(N - 1) - i].seq;
                    Team team = teamsPool[pointBoard[(t - 1) % N][(N - 1) - i].teamIdx];
                    team.headFirst = !team.headFirst;
                    swap(team);

                    printBoard(pointBoard);

                    System.out.println(team.idx + " 팀의 "+ score +" 번째 사람이 공을 얻어 점수를 얻고 방향을 바꿉니다.");
                    System.out.println("score : " + (score * score));

                    ans += (score * score);

                    System.out.println("ans : " + ans);
                    System.out.println();
                    return;
                }
//                printBoard(test);
            }

        } else if (3 * N + 1 <= t || t == 0) {
//            System.out.println(":: Case 4 : " + t + " 턴");
            if (t == 0) t = turn;

            for (int i = 0; i < N; i++) {
                test[i][(N - 1) - ((t - 1) % N)] = 4;
                printBoard(test);
                if (pointBoard[i][(N - 1) - ((t - 1) % N)] != null) {
                    printBoard(pointBoard);

                    int score = pointBoard[i][(N - 1) - ((t - 1) % N)].seq;
                    Team team = teamsPool[pointBoard[i][(N - 1) - ((t - 1) % N)].teamIdx];
                    team.headFirst = !team.headFirst;
                    swap(team);

                    printBoard(pointBoard);

                    System.out.println(team.idx + " 팀의 "+ score +" 번째 사람이 공을 얻어 점수를 얻고 방향을 바꿉니다.");
                    System.out.println("score : " + (score * score));

                    ans += (score * score);

                    System.out.println("ans : " + ans);
                    System.out.println();

                    return;
                }
//                printBoard(test);
            }
        }
    }

    // head 로 부터 연결된 노드들의 순서를 변경한다.
    static void swap(Team team) {
        // 머리 방향으로 바뀌었을 때
        if (team.headFirst) {
            Point tail = team.tail;
            Point head = team.head;

            int changeSeq = head.seq;
            System.out.println("변경할 seq = " + changeSeq);

            for (Point p = tail; p != head.prev; p = p.prev) {
                System.out.print("변경 전 seq = " + p.seq + "   -> ");
                p.seq = changeSeq--;
                System.out.println("변경 후 seq = " + p.seq);
            }
        }
        // 꼬리 방향으로 바뀌었을 때
        else {
            Point head = team.head;
            Point tail = team.tail;

            int changeSeq = tail.seq;
            System.out.println("변경할 seq = " + changeSeq);

            for (Point p = head; p != tail.next; p = p.next) {
                System.out.print("변경 전 seq = " + p.seq + "   -> ");
                p.seq = changeSeq--;
                System.out.println("변경 후 seq = " + p.seq);
            }
        }
    }

    static void printBoard(int[][] b) {
        System.out.println(":: Ball Test ::");
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                System.out.print(b[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printBoard(Point[][] pb) {
        System.out.println(":: Point BOARD STATUS :: ");
        for (int i = 0; i < pb.length; i++) {
            for (int j = 0; j < pb[i].length; j++) {
                if (pb[i][j] == null) System.out.print(0 + "\t");
                else System.out.print(pb[i][j].seq + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printBoard(boolean[][] b) {
        System.out.println(":: Road Test ::");
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                if (b[i][j]) System.out.print(1 + "\t");
                else System.out.print(0 + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printBoard() {
        System.out.println(":: BOARD STATUS ::");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }


}