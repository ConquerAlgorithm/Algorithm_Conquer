package org.example.BOJ;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// 참고한 블로그: https://ryuls.tistory.com/10
public class 미로탐색 {

    public static int N, M;
    public static int[][] map;
    public static boolean[][] visited;
    public static int count = 1;
    public static int[] dy = {1, 0, -1, 0};
    public static int[] dx = {0, 1, 0, -1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();

        map = new int[N][M];
        visited = new boolean[N][M];

        String str;

        for (int i = 0; i < N; i++) {
            str = sc.next();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j) - '0';
            }
        }
        bfs();

        System.out.println(map[N - 1][M - 1]);
    }

    public static void bfs() {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        int[] tmp = new int[2];
        visited[0][0] = true;
        int x, y;
        int nx, ny;

        while (!queue.isEmpty()) {
            tmp = queue.poll();
            x = tmp[1];
            y = tmp[0];

            for (int i = 0; i < 4; i++) {
                nx = x + dx[i];
                ny = y + dy[i];

                if (ny < 0 || nx < 0 || ny >= N || nx >= M) {
                    continue;
                }
                if (!visited[ny][nx] && map[ny][nx] == 1) {
                    visited[ny][nx] = true;
                    map[ny][nx] += map[y][x];
                    queue.add(new int[]{ny, nx});
                }
            }
        }

    }

}

