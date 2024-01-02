package BackJoon.골드.G3.BJ_16235_나무_재테크;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    // 상 우상 우 우하 하 좌하 좌 좌상
    static final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static final int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    static int N, M, K;
    static int[][] notoriousBoard;
    static int[][] addBoard;
    static Deque<Tree>[][] treeBoard;
    static Queue<Tree> deadTreeList;

    static void input() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 나무 상태 관리 격자
        treeBoard = new ArrayDeque[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                treeBoard[i][j] = new ArrayDeque<>();
            }
        }

        // 죽은 나무 관리할 Queue
        deadTreeList = new LinkedList<>();

        // 초기 영양분 5
        notoriousBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                notoriousBoard[i][j] = 5;
            }
        }

        // 겨울에 로봇이 추가할 양분 정보 입력
        addBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                addBoard[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 나무 정보 입력 - 초기 입력 나무는 겹치지 않는다.
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int age = Integer.parseInt(st.nextToken());

            Tree tree = new Tree(x, y, age);

            treeBoard[x][y].addFirst(tree);
        }
    }
    public static void main(String[] args) throws Exception {
        input();
        solve();
        System.out.println(getAnswer());
    }

    static void solve() {
        while (K-- > 0) {
            spring();
            summer();
            autumn();
            winter();
        }
    }

    // 나무들이 나이만큼 양분을 먹고 나이가 1증가
    static void spring() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 나무들이 있으면
                if (!treeBoard[i][j].isEmpty()) {
                    Deque<Tree> treeList = treeBoard[i][j];

                    // 자신의 나이만큼 해당칸에 있는 양분을 먹고 나이를 1증가시킨다.
                    for (Tree tree: treeList) {
                        // 양분이 나무 나이만큼 없다면 해당 나무는 죽은 나무 리스트에 추가한다.
                        if (notoriousBoard[i][j] < tree.age) {
                            deadTreeList.add(tree);
                        }
                        // 나무 나이만큼 양분이 충분하다면 나무는 나이를 1 증가시키고 양분은 나무 나이만큼 감소
                        else {
                            notoriousBoard[i][j] -= tree.age;
                            tree.age++;
                        }
                    }
                }

            }
        }

    }

    // 죽은 나무들이 각자의 나이 / 2 만큼 양분을 제자리에 놓는다.
    static void summer() {
        for (Tree d_tree: deadTreeList) {
            // 죽은 나무들은 해당 좌표에 자신의 나이만큼 양분을 남긴다.
            int x = d_tree.x;
            int y = d_tree.y;
            int notorious = d_tree.age / 2;

            notoriousBoard[x][y] += notorious;

            // 해당 좌표 나무 리스트에서 죽은나무 객체 삭제
            treeBoard[x][y].remove(d_tree);
        }

        // 처리가 끝나면 리스트 초기화
        deadTreeList.clear();
    }

    // 나이가 5의 배수인 나무들 기준 8방으로 나이가 1인 나무들이 번식한다.
    static void autumn() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                Deque<Tree> treeList = treeBoard[i][j];

                if (treeList.isEmpty()) continue;

                for (Tree tree: treeList) {
                    if (tree.age % 5 == 0) {
                        int cx = tree.x;
                        int cy = tree.y;

                        for (int d = 0; d < 8; d++) {
                            int nx = cx + dx[d];
                            int ny = cy + dy[d];

                            if (isNotBoundary(nx, ny)) continue;

                            // 나이가 1인 나무 번식
                            treeBoard[nx][ny].addFirst(new Tree(nx, ny, 1));
                        }
                    }
                }
            }
        }
    }

    static boolean isNotBoundary(int x, int y) {
        return !(0 <= x && x < N && 0 <= y && y < N);
    }

    // S2D2가 addBoard 대로 양분을 추가한다.
    static void winter() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                notoriousBoard[i][j] += addBoard[i][j];
            }
        }
    }

    static int getAnswer() {
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cnt += treeBoard[i][j].size();
            }
        }

        return cnt;
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Tree extends Point {
        int age;

        public Tree(int x, int y, int age) {
            super(x, y);
            this.age = age;
        }
    }

    static void printBoard(int[][] b) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(b[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
