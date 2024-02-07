package BackJoon.골드.G2.BJ_12100_2048;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static int N, ans;
    static int[][] board;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        board = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 5번 이동을 시킬건데 상 하 좌 우 로 이동시켜봐서 가장 크게 만들어지는 블록을 찾아야함
        progress(0);
        System.out.println(ans);
    }

    static void progress(int cnt) {
        if (cnt == 5) {
            findMax();
            return;
        }

        int[][] tmp = new int[N][N];
        for (int i = 0; i < N; i++) {
            tmp[i] = board[i].clone();
        }

        for (int d = 0; d < 4; d++) {
            move(d);
            progress(cnt + 1);
            for (int i = 0; i < N; i++) {
                board[i] = tmp[i].clone();
            }
        }

    }

    static void findMax() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ans = Math.max(ans, board[i][j]);
            }
        }
    }

    static void move(int d) {
        switch (d) {
            // 상
            case 0:
                for (int i = 0; i < N; i++) {
                    int idx = 0;
                    int block = 0;

                    for (int j = 0; j < N; j++) {
                        if (board[j][i] != 0) {
                            if (block == board[j][i]) {
                                board[idx - 1][i] = block * 2;
                                block = 0;
                                board[j][i] = 0;
                            } else {
                                block = board[j][i];
                                board[j][i] = 0;
                                board[idx][i] = block;
                                idx++;
                            }
                        }
                    }
                }
                break;
            // 우
            case 1:
                for (int i = 0; i < N; i++) {
                    int idx = N - 1;
                    int block = 0;

                    for (int j = N - 1; j >= 0; j--) {
                        if (board[i][j] != 0) {
                            if (block == board[i][j]) {
                                board[i][idx + 1] = block * 2;
                                block = 0;
                                board[i][j] = 0;
                            } else {
                                block = board[i][j];
                                board[i][j] = 0;
                                board[i][idx] = block;
                                idx--;
                            }
                        }
                    }
                }
                break;
            // 하
            case 2:
                for (int i = 0; i < N; i++) {
                    int idx = N - 1;
                    int block = 0;

                    for (int j = N - 1; j >= 0; j--) {
                        if (board[j][i] != 0) {
                            if (block == board[j][i]) {
                                board[idx + 1][i] = block * 2;
                                block = 0;
                                board[j][i] = 0;
                            } else {
                                block = board[j][i];
                                board[j][i] = 0;
                                board[idx][i] = block;
                                idx--;
                            }
                        }
                    }
                }
                break;
            // 좌
            case 3:
                for (int i = 0; i < N; i++) {
                    int idx = 0;
                    int block = 0;

                    for (int j = 0; j < N; j++) {
                        if (board[i][j] != 0) {
                            if (block == board[i][j]) {
                                board[i][idx - 1] = block * 2;
                                block = 0;
                                board[i][j] = 0;
                            } else {
                                block = board[i][j];
                                board[i][j] = 0;
                                board[i][idx] = block;
                                idx++;
                            }
                        }
                    }
                }
                break;

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
